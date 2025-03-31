package com.Thiago_landi.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.Thiago_landi.libraryapi.model.Author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record AuthorDTO(
		UUID id,
		@NotBlank(message = "campo obrigatorio") // essa @ garante que nao seja mandado uma string vazia ou null
		@Size(min = 2, max = 100, message = "campo fora do tamanho padrão")
		String name, 
		@NotNull(message = "campo obrigatorio")
		@Past(message = "nao pode ser uma data futura")
		LocalDate dateBirth, 
		@NotBlank(message = "campo obrigatorio")
		@Size(min = 2, max = 50, message = "campo fora do tamanho padrão")
		String nationality ) {

	
	// serve para passar o AuthorDTO recebido no postman para a entidade Author
	public Author mapForAuthor() {
		Author author = new Author();
		author.setId(id);
		author.setName(this.name);
		author.setDateBirth(this.dateBirth);
		author.setNationality(this.nationality);
		return author;
	}
}
