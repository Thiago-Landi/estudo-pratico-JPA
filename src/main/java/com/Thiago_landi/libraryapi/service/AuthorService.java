package com.Thiago_landi.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Thiago_landi.libraryapi.exceptions.InvalidOperationException;
import com.Thiago_landi.libraryapi.model.Author;
import com.Thiago_landi.libraryapi.repository.AuthorRepository;
import com.Thiago_landi.libraryapi.repository.BookRepository;
import com.Thiago_landi.libraryapi.validator.AuthorValidator;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private AuthorValidator validator;
	
	@Autowired
	private BookRepository bookRepository;
	
	public Author save(Author author) {
		validator.validate(author);
		return authorRepository.save(author);
	}
	
	public Optional<Author> findById(UUID id) {
		return authorRepository.findById(id);
	}
	
	public void delete(Author author) {
		if(existBook(author)) {
			throw new InvalidOperationException(
					"Não é permitido excluir um autor que possui livros cadastrados!");
		}
		
		authorRepository.delete(author);
	}
	
	public List<Author> search(String name, String nationality) {
		if(name != null && nationality != null) {
			return authorRepository.findByNameAndNationality(name, nationality);
		}
		
		if(name != null) {
			return authorRepository.findByName(name);
		}
		
		if(nationality != null) {
			return authorRepository.findByNationality(nationality);
		}
		
		return authorRepository.findAll();
	}
	
	public Author update(UUID id, Author author) {
		
			Optional<Author> optionalAuthor = authorRepository.findById(id);
			 if (optionalAuthor.isEmpty()) {
			        throw new IllegalArgumentException("O autor com o ID fornecido não existe no banco.");
			    }
		
			Author entity = optionalAuthor.get();

		    validator.validate(author);
			updateData(entity, author);
			return authorRepository.save(entity);
		
		
	}
	
	public void updateData(Author author, Author obj) {
		author.setName(obj.getName());
		author.setNationality(obj.getNationality());
		author.setDateBirth(obj.getDateBirth());
	}
	
	public boolean existBook(Author author) {
		return bookRepository.existsByAuthor(author);
	}
	
}
