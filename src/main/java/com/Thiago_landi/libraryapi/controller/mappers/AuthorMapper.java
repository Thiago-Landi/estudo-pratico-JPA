package com.Thiago_landi.libraryapi.controller.mappers;

import org.mapstruct.Mapper;

import com.Thiago_landi.libraryapi.controller.dto.AuthorDTO;
import com.Thiago_landi.libraryapi.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

	Author toEntity(AuthorDTO dto);
	
	AuthorDTO toDTO(Author author);
}
