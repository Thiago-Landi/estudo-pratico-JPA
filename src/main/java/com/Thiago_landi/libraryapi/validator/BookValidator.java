package com.Thiago_landi.libraryapi.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Thiago_landi.libraryapi.exceptions.InvalidFieldException;
import com.Thiago_landi.libraryapi.exceptions.RegistryDuplicateException;
import com.Thiago_landi.libraryapi.model.Book;
import com.Thiago_landi.libraryapi.repository.BookRepository;

@Component
public class BookValidator {

    private static final int YEAR_PRICE_REQUIREMENT = 2020;
	
	@Autowired
	private BookRepository repository;
	
	public void validate(Book book) {
		if(existBookIsbn(book)) {
			throw new RegistryDuplicateException("isbn já cadastrado");
		}
		
		if(isPriceMandatoryNull(book)) {
			throw new InvalidFieldException("price", "Para livros com ano de publicação a partir de 2020, o preço é obrigatório.");
		}
	}
	
	private boolean existBookIsbn(Book book) {
		Optional<Book> bookOptional = repository.findByIsbn(book.getIsbn());
		
		if(book.getId() == null) {
			return bookOptional.isPresent();
		}
		
		if(bookOptional.isPresent()) {
			Book existing = bookOptional.get();
			
			return !book.getId().equals(existing.getId());
		}
		
		// caso nenhuma autor foi encontrado, ta permitido o cadastro/edição.
		return false;
	}
	
	private boolean isPriceMandatoryNull(Book book) {
		return book.getPrice() == null && 
				book.getDatePublication().getYear() >= YEAR_PRICE_REQUIREMENT;
	}
	
	
}
