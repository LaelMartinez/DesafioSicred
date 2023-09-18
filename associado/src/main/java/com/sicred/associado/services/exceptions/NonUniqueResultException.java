package com.sicred.associado.services.exceptions;

public class NonUniqueResultException extends javax.persistence.NonUniqueResultException {
	private static final long serialVersionUID = 1L;

	public NonUniqueResultException(String msg) {
		super(msg);
	}
}
