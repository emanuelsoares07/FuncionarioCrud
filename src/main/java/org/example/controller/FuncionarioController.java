package org.example.controller;

import io.quarkus.hibernate.orm.runtime.session.TransactionScopedSession;
import org.example.dto.FuncionarioDTO;
import org.example.orm.Funcionario;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;


@Transactional
@ApplicationScoped
public class FuncionarioController {
    @PersistenceUnit
    private EntityManager entityManager;

    public Response cadastrarFuncionario(FuncionarioDTO funcionarioDTO) {
        // Verifique se todos os campos necessários estão presentes
        if (funcionarioDTO.getDsNome() == null || funcionarioDTO.getDsSobrenome() == null || funcionarioDTO.getDsEmail() == null || funcionarioDTO.getDsPis() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Dados Incompletos").build();
        }

        if (PisDuplicado(funcionarioDTO.getDsPis())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Numero de pis já cadastrado").build();
        }
        // Converter o DTO para a entidade Funcionario
        Funcionario funcionario = new Funcionario();
        funcionario.setDsNome(funcionarioDTO.getDsNome());
        funcionario.setDsSobrenome(funcionarioDTO.getDsSobrenome());
        funcionario.setDsEmail(funcionarioDTO.getDsEmail());
        funcionario.setDsPis(funcionarioDTO.getDsPis());
        funcionario.setDtCadastro(LocalDateTime.now());
        funcionario.persist();

        return Response.status(Response.Status.CREATED).entity(funcionario).build();
    }

    public boolean PisDuplicado(String dsPis) {

        TypedQuery<Funcionario> query = entityManager.createQuery(
                "SELECT f FROM Funcionario f WHERE f.dsPis = :dsPis", Funcionario.class);
        query.setParameter("dsPis", dsPis);
        List<Funcionario> funcionarios = query.getResultList();

        return !funcionarios.isEmpty();

    }

    public Response atualizarFuncionario(FuncionarioDTO funcionarioDTO) {

        try {
            if (funcionarioDTO.getIdFuncionario() == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("ID do funcionário não fornecido").build();
            }

            Funcionario funcionario = Funcionario.findById(funcionarioDTO.getIdFuncionario());
            if (funcionario == null){
                return Response.status(Response.Status.NOT_FOUND).entity("Funcionário não encontrado").build();
            }

            funcionario.setDsNome(funcionarioDTO.getDsNome());
            funcionario.setDsSobrenome(funcionarioDTO.getDsSobrenome());
            funcionario.setDsEmail(funcionarioDTO.getDsEmail());
            funcionario.setDsPis(funcionarioDTO.getDsPis());

            return Response.ok(funcionario).build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Funcionário não encontrado").build();
        }

    }

    public Response deleteFuncionario(Long id) {

        try {
            Funcionario funcionario = Funcionario.findById(id);
            if (funcionario == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Funcionário não encontrado").build();
            }
            funcionario.delete();
        return Response.ok("Funcionario excluído com sucesso").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o funcionário").build();
        }
    }
}