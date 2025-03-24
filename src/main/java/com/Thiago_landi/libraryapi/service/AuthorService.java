package com.Thiago_landi.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
	
	public Optional<Author> findById(UUID id) {
		return repository.findById(id);
	}
	
	public void delete(Author author) {
		repository.delete(author);
	}
	
	public List<Author> search(String name, String nationality) {
		if(name != null && nationality != null) {
			return repository.findByNameAndNationality(name, nationality);
		}
		
		if(name != null) {
			return repository.findByName(name);
		}
		
		if(nationality != null) {
			return repository.findByNationality(nationality);
		}
		
		return repository.findAll();
	}
	
	public Author update(UUID id, Author author) {
		try {
			Author entity = repository.getReferenceById(id);
			updateData(entity, author);
			return repository.save(entity);
		}catch (Exception e) {
			  throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja salvo na base.");
		}
		
	}
	
	public void updateData(Author author, Author obj) {
		author.setName(obj.getName());
		author.setNationality(obj.getNationality());
		author.setDateBirth(obj.getDateBirth());
	}
	
}
