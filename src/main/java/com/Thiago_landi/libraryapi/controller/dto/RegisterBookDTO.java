package com.Thiago_landi.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.Thiago_landi.libraryapi.model.GenderBook;

//requisição do book
public record RegisterBookDTO(
		String isbn,
		String title,
		LocalDate datePublication,
		GenderBook gender,
		BigDecimal price,
		UUID idAuthor) {

}
