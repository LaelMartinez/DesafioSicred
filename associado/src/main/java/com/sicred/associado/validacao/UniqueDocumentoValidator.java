package com.sicred.associado.validacao;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.sicred.associado.entities.Associado;
import com.sicred.associado.repositories.AssociadoRepository;

public class UniqueDocumentoValidator implements ConstraintValidator<UniqueDocumento, String> {

    @Autowired
    private AssociadoRepository repository;

    @Override
    public void initialize(UniqueDocumento constraintAnnotation) {
    }

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		Optional<Associado> obj = repository.findByDocumento1(value);
		if (obj!= null) {
			return false;
		}else {
			return true;
		}
	}
}

