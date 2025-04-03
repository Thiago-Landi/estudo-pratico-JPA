package com.Thiago_landi.libraryapi.service;

import static com.Thiago_landi.libraryapi.repository.specs.BookSpecs.genderEqual;
import static com.Thiago_landi.libraryapi.repository.specs.BookSpecs.isbnEqual;
import static com.Thiago_landi.libraryapi.repository.specs.BookSpecs.nameAuthorLike;
import static com.Thiago_landi.libraryapi.repository.specs.BookSpecs.titleLike;
import static com.Thiago_landi.libraryapi.repository.specs.BookSpecs.yearPublicationEqual;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.Thiago_landi.libraryapi.controller.dto.RegisterBookDTO;
import com.Thiago_landi.libraryapi.controller.mappers.BookMapper;
import com.Thiago_landi.libraryapi.model.Author;
import com.Thiago_landi.libraryapi.model.Book;
import com.Thiago_landi.libraryapi.model.GenderBook;
import com.Thiago_landi.libraryapi.repository.AuthorRepository;
import com.Thiago_landi.libraryapi.repository.BookRepository;
import com.Thiago_landi.libraryapi.validator.BookValidator;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookMapper mapper;
	
	@Autowired
	private BookValidator validator;
	
	public Book save(Book book) {
		validator.validate(book);
		return bookRepository.save(book);
	}
	
	public Optional<Book> findById(UUID id) {
		return bookRepository.findById(id);
	}
	
	public void delete(Book book) {
		bookRepository.delete(book);
	}
	
	public List<Book> search(
			String isbn, String title, String nameAuthor, GenderBook gender, Integer yearPublication){
		
		Specification<Book> specs = Specification.where((root, query, cb)  -> cb.conjunction());
		
		if(isbn != null) {
			// não precisa chamar a classe BookSpecs pq fiz o import dela la em cima
			specs = specs.and(isbnEqual(isbn));
		}
		
		if(title != null) {
			specs = specs.and(titleLike(title));
		}
		
		if(gender != null) {
			specs = specs.and(genderEqual(gender));
		}
		
		if(yearPublication != null) {
			specs = specs.and(yearPublicationEqual(yearPublication));
		}
		
		if(nameAuthor != null) {
			specs = specs.and(nameAuthorLike(nameAuthor));
		}
		
		return bookRepository.findAll(specs);
	}
	
	public void update(UUID id, RegisterBookDTO dto) {
		Book entity = bookRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("O livro com o ID fornecido não existe no banco."));
	
		Author author = authorRepository.findById(dto.idAuthor())
				.orElseThrow(() -> new IllegalArgumentException("O autor com o ID fornecido não existe no banco."));
		
		
		
		Book bookTemp = mapper.toEntity(dto);
		validator.validate(bookTemp);
		
		updateData(entity, dto, author);
		bookRepository.save(entity);
	}
	
	private void updateData(Book book, RegisterBookDTO dto, Author author) {
		book.setDatePublication(dto.datePublication());
		book.setIsbn(dto.isbn());
		book.setPrice(dto.price());
		book.setGender(dto.gender());
		book.setTitle(dto.title());
		book.setAuthor(author);
	}
	
}
