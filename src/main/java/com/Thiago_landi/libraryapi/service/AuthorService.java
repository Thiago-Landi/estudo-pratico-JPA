package com.Thiago_landi.libraryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Thiago_landi.libraryapi.model.Author;
import com.Thiago_landi.libraryapi.repository.AuthorRepository;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository repository;
	
	public Author save(Author author) {
		return repository.save(author);
	}
	
}
