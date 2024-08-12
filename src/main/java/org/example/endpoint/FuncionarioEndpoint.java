package org.example.endpoint;


import org.example.controller.FuncionarioController;
import org.example.dto.FuncionarioDTO;
import org.example.orm.Funcionario;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Transactional
@Path("/funcionario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FuncionarioEndpoint  {

    @Inject
    FuncionarioController funcionarioController;
    @POST
    public Response cadastrarFuncionario(FuncionarioDTO funcionarioDTO){
          return funcionarioController.cadastrarFuncionario(funcionarioDTO);
    }
    @PUT
    public Response atualizarFuncionario(FuncionarioDTO funcionarioDTO) {
        return funcionarioController.atualizarFuncionario(funcionarioDTO);
    }
    @GET
    @Path("/listpaginacao")
    public List<Funcionario> buscarTodosFuncionarios(){
        return Funcionario.listAll();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteFuncionario(@PathParam("id") Long id) {
        return funcionarioController.deleteFuncionario(id);
    }


}