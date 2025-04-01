package com.Thiago_landi.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import com.Thiago_landi.libraryapi.model.GenderBook;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

//requisição do book
public record RegisterBookDTO(
		@ISBN
		@NotBlank(message = "campo obrigatorio")
		String isbn,
		@NotBlank(message = "campo obrigatorio")
		String title,
		@NotNull(message = "campo obrigatorio")
		@Past
		LocalDate datePublication,
		GenderBook gender,
		BigDecimal price,
		@NotNull(message = "campo obrigatorio")
		UUID idAuthor) {

}
