package com.Thiago_landi.libraryapi.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Thiago_landi.libraryapi.controller.dto.RegisterBookDTO;
import com.Thiago_landi.libraryapi.controller.mappers.BookMapper;
import com.Thiago_landi.libraryapi.model.Book;
import com.Thiago_landi.libraryapi.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("books")

public class BookController implements GenericController {

	@Autowired
	private BookService service;
	
	@Autowired
	private BookMapper mapper;
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid RegisterBookDTO dto){
		Book book = mapper.toEntity(dto);
		service.save(book);
			
		URI location = generateHeaderlocation(book.getId());
		return ResponseEntity.created(location).build();
	}
}
