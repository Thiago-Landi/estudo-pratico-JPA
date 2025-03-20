package com.Thiago_landi.libraryapi.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Thiago_landi.libraryapi.controller.dto.AuthorDTO;
import com.Thiago_landi.libraryapi.model.Author;
import com.Thiago_landi.libraryapi.service.AuthorService;

@RestController
@RequestMapping("/authors")
public class AuthorController {

	@Autowired
	private AuthorService service;
	
	@PostMapping
	public ResponseEntity<Void> save(@RequestBody AuthorDTO author) {
		Author authorModel = author.mapForAuthor();
		service.save(authorModel);
		
		// esse codigo constroi a url para acessar o author criado (URL:http://localhost:8080/authors/{id}
		URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(authorModel.getId())
        .toUri();
		
		return ResponseEntity.created(location).build();
	}
}
