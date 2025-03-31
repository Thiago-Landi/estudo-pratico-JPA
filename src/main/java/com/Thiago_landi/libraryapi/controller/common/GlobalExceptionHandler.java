package com.Thiago_landi.libraryapi.controller.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Thiago_landi.libraryapi.controller.dto.ErrorField;
import com.Thiago_landi.libraryapi.controller.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ErrorResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<FieldError> fieldErrors = e.getFieldErrors();
		List<ErrorField> listErrors = fieldErrors.stream()
				.map(fe -> new ErrorField(fe.getField(), fe.getDefaultMessage()))
				.collect(Collectors.toList());
		return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), 
				"erro de validação", 
				listErrors);
	}
}
