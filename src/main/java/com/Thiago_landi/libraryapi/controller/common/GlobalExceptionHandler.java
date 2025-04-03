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
import com.Thiago_landi.libraryapi.exceptions.InvalidFieldException;
import com.Thiago_landi.libraryapi.exceptions.InvalidOperationException;
import com.Thiago_landi.libraryapi.exceptions.RegistryDuplicateException;

@RestControllerAdvice //Isso faz com que ela intercepte exceções lançadas dentro de qualquer controller
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
	
	@ExceptionHandler(RegistryDuplicateException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse handlerRegistryDuplicateException(RegistryDuplicateException e) {
		return ErrorResponse.conflict(e.getMessage());
	}
	
	@ExceptionHandler(InvalidOperationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handlerInvalidOperationException(InvalidOperationException e) {
		return ErrorResponse.responseDefault(e.getMessage());
	}
	
	@ExceptionHandler(InvalidFieldException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ErrorResponse handlerInvalidFieldException(InvalidFieldException e) {
		return new ErrorResponse(
				HttpStatus.UNPROCESSABLE_ENTITY.value(),
				"Erro de validação", 
				List.of(new ErrorField(e.getField(), e.getMessage())));
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handlerUnhandledErrors(RuntimeException e) {
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				"Ocorreu um erro inesperado. Entre em contato com a administração", 
				List.of());
	}
}
