package com.Thiago_landi.libraryapi.repository.specs;

import org.springframework.data.jpa.domain.Specification;

import com.Thiago_landi.libraryapi.model.Book;
import com.Thiago_landi.libraryapi.model.GenderBook;

public class BookSpecs {

	
	public static Specification<Book> isbnEqual(String isbn){
		// root pega da entidade/classe
		return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
	}
	
	public static Specification<Book> titleLike(String title){
		// like compara sem precisar ta um nome completo, os upper é pra não precisar se preocupar com letra maiuscula e os % pra dizer que pode ser tanto no inicio quanto no fim a procura da palavra
		return (root, query, cb) -> 
				cb.like(cb.upper(root.get("title")), "%" + title.toUpperCase() + "%");
	}
	
	public static Specification<Book> genderEqual(GenderBook gender){
		return (root, query, cb) -> cb.equal(root.get("gender"), gender);
	}
	
	public static Specification<Book> yearPublicationEqual(Integer yearPublication){
		return (root, query, cb) -> 
				cb.equal(cb.function("to_char", String.class, 
				root.get("datePublication"), cb.literal("YYYY")), yearPublication.toString());
	}
	
	public static Specification<Book> nameAuthorLike(String name) {
		return (root, query, cb) -> 
				cb.like(cb.upper(root.get("author").get("name")), "%" + name.toUpperCase() + "%");
	}
}
