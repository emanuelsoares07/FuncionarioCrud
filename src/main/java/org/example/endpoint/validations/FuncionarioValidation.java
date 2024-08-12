package org.example.endpoint.validations;

import javax.validation.groups.Default;

public interface FuncionarioValidation {

    interface Cadastrar extends Default {}

    interface Editar extends Default {}
}