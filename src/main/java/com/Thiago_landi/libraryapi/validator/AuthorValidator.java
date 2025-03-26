package com.Thiago_landi.libraryapi.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Thiago_landi.libraryapi.exceptions.RegistryDuplicateException;
import com.Thiago_landi.libraryapi.model.Author;
import com.Thiago_landi.libraryapi.repository.AuthorRepository;

@Component
public class AuthorValidator {

	@Autowired
	private AuthorRepository repository;
	
	public void validate(Author author) {
		if(thereRegisteredAuthor(author)) {
			throw new RegistryDuplicateException("Autor já cadastrado");
		}
	}
	
	private boolean thereRegisteredAuthor(Author author) {
		Optional<Author> optional = repository.findByNameAndDateBirthAndNationality(
				author.getName(), author.getDateBirth(), author.getNationality());
		
		//Se o ID for null , significa que estamos tentando cadastrar um novo autor
		if(author.getId() == null) {
			return optional.isPresent();// se retornar true, quer dizer que existe um autor com os mesmos dados
										// se retornar false, quer dizer que não existe autor igual e ta permitido o cadastro
		}
		
		//Aqui tratamos do caso de edição de um autor já existente
		if(optional.isPresent()) {
			Author existing = optional.get();
			
			// se o id for igual, significa que ta tentando atualizar um autor(Não há duplicação) retorna false, ta permitido o update
			// se o id for diferente, significa que já existe outro autor com os mesmo dados, então há duplicação, retorna true
			return !author.getId().equals(existing.getId());
		}
		
		// caso nenhuma autor foi encontrado, ta permitido o cadastro/edição.
		return false;
	}
}
