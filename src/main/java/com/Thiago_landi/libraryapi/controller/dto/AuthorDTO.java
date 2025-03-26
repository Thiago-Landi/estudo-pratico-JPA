package com.Thiago_landi.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.Thiago_landi.libraryapi.model.Author;

public record AuthorDTO(UUID id ,String name, LocalDate dateBirth, String nationality ) {

	
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
