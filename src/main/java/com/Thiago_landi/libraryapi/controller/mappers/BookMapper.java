package com.Thiago_landi.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.Thiago_landi.libraryapi.controller.dto.RegisterBookDTO;
import com.Thiago_landi.libraryapi.model.Book;
import com.Thiago_landi.libraryapi.repository.AuthorRepository;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

	@Autowired
	AuthorRepository authorRepository;
	
	//pega o id que o dto recebe e coloca como author na entidade book
	@Mapping(target = "author", expression = "java( authorRepository.findById(dto.idAuthor()).orElse(null) )")
	public abstract Book toEntity(RegisterBookDTO dto);
}
