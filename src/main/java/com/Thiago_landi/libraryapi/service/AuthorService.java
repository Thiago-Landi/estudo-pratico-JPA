package com.Thiago_landi.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.Thiago_landi.libraryapi.controller.dto.AuthorDTO;
import com.Thiago_landi.libraryapi.controller.mappers.AuthorMapper;
import com.Thiago_landi.libraryapi.exceptions.InvalidOperationException;
import com.Thiago_landi.libraryapi.model.Author;
import com.Thiago_landi.libraryapi.model.UserClass;
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
	
	@Autowired
	private AuthorMapper mapper;
	
	@Autowired
	private SecurityService securityService;
	
	public Author save(Author author) {
		validator.validate(author);
		UserClass user = securityService.getLoggedUser();
		author.setUser(user);
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
	
	public List<Author> searchByExample(String name, String nationality){
		var author = new Author();
		author.setName(name);
		author.setNationality(nationality);
		
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withIgnoreNullValues()
				.withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example<Author> authorExample = Example.of(author, matcher);
		return authorRepository.findAll(authorExample);
	}
	
	public void update(UUID id, AuthorDTO author) {
		
			Author entity = authorRepository.findById(id)
					.orElseThrow( () -> new IllegalArgumentException("O autor com o ID fornecido não existe no banco."));

		  Author authorTemp = mapper.toEntity(author);
			
			validator.validate(authorTemp);
			updateData(entity, author);		
			authorRepository.save(entity);
	}
	
	private void updateData(Author author, AuthorDTO obj) {
		author.setName(obj.name());
		author.setNationality(obj.nationality());
		author.setDateBirth(obj.dateBirth());
	}
	
	public boolean existBook(Author author) {
		return bookRepository.existsByAuthor(author);
	}
	
}
