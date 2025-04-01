package com.Thiago_landi.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.Thiago_landi.libraryapi.model.GenderBook;

// resposta do book
public record SearchBookDTO(
		UUID id,
		String isbn,
		String title,
		LocalDate datePublication,
		GenderBook gender,
		BigDecimal price,
		AuthorDTO author){ 
}
