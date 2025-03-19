package com.Thiago_landi.libraryapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Thiago_landi.libraryapi.model.Author;
import com.Thiago_landi.libraryapi.model.Book;
import com.Thiago_landi.libraryapi.model.GenderBook;
import com.Thiago_landi.libraryapi.repository.AuthorRepository;
import com.Thiago_landi.libraryapi.repository.BookRepository;

@Service
public class TransactionService {

	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Transactional
	public void execute() {
		// salva o autor
        Author author = new Author();
        author.setName("teste do Francisco");
        author.setNationality("Brasileira");
        author.setDateBirth(LocalDate.of(1951, 1, 31));

        authorRepository.save(author);

        // salva o livro
        Book book = new Book();
        book.setIsbn("90887-84874");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(GenderBook.FICTION);
        book.setTitle(" teste Livro do Francisco");
        book.setDatePublication(LocalDate.of(1980, 1, 2));

        book.setAuthor(author);

        bookRepository.save(book);

        if(author.getName().equals("teste do Francisco")){
            throw new RuntimeException("Rollback!");
        }
	}
}
