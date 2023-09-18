package com.sicred.associado.validacao;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueDocumentoValidator.class)
public @interface UniqueDocumento {
    String message() default "Documento já existe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
