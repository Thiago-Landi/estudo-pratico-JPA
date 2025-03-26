package com.Thiago_landi.libraryapi.exceptions;

public class RegistryDuplicateException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public RegistryDuplicateException(String message) {
		super(message);
	}

}
