package org.example.orm;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "funcionario")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_funcionario", sequenceName = "seq_funcionario", allocationSize = 1)
public class Funcionario extends PanacheEntityBase implements Serializable {

    @Id
    @Column(name = "id_funcionario", unique = true, updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_funcionario")
    private Long idFuncionario;

    @Column(name = "ds_nome", nullable = false)
    @Size(min = 2, max = 30)
    private String dsNome;

    @Column(name = "ds_sobrenome", nullable = false, length = 300)
    @Size(min = 2, max = 50)
    private String dsSobrenome;

    @Column(name = "ds_email")
    @Email(message = "E-mail inválido")
    private String dsEmail;

    @NotBlank(message = "O campo pis é obrigatório")
    @Column(name = "ds_pis", unique = true, length = 11)
    private String dsPis;

    @Column(name = "dt_Cadastro", nullable = false, updatable = false)
    private LocalDateTime dtCadastro;

}