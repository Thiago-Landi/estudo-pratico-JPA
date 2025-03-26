package com.Thiago_landi.libraryapi.controller.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

public record ErrorResponse(int status, String message, List<ErrorField> errors) {

	public static ErrorResponse responseDefault(String mensage) {
		return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), mensage, List.of());
	}
	
	public static ErrorResponse conflict(String mensage) {
		return new ErrorResponse(HttpStatus.CONFLICT.value(), mensage, List.of());
	}
	
}
