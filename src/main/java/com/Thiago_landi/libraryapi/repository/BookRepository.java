package com.Thiago_landi.libraryapi.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thiago_landi.libraryapi.model.Author;
import com.Thiago_landi.libraryapi.model.Book;

public interface BookRepository extends JpaRepository<Book, UUID>{

	// Query Method
    // select * from book where id_author = id
	List<Book> findByAuthor(Author author);
	
    // select * from book where title = title
	List<Book> findByTitle(String title);
	
    // select * from book where isbn = ?
	List<Book> findByIsbn (String isbn);
	
    // select * from book where title = ? and price = ?	
	List<Book> findByTitleAndPrice(String title, BigDecimal price);
	
    // select * from book where title = ? or isbn = ?
	List<Book> findByTitleOrIsbn(String title, String isbn);
}
