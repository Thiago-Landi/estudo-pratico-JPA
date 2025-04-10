package com.Thiago_landi.libraryapi;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Thiago_landi.libraryapi.model.Author;
import com.Thiago_landi.libraryapi.model.Book;
import com.Thiago_landi.libraryapi.model.GenderBook;
import com.Thiago_landi.libraryapi.repository.AuthorRepository;
import com.Thiago_landi.libraryapi.repository.BookRepository;

@SpringBootTest
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	
	@Test
	void save() {
		Book book = new Book();
		
		book.setIsbn("0973-7930");
        book.setPrice(BigDecimal.valueOf(300));
        book.setGender(GenderBook.SCIENCE);
        book.setTitle("Ciencias");
        book.setDatePublication(LocalDate.of(1990, 3, 2));
        
        Author author = authorRepository.findById(UUID.fromString("d4aa8ad8-d88b-4c46-bf16-acf5c3e04147"))
        		.orElse(null);
        
        book.setAuthor(author);
        bookRepository.save(book);
	}
	
	
	@Test
	void updateBookAuthor() {
		UUID idBook = UUID.fromString("9e9cc135-7923-4149-a46b-a11e98af375d");
		Book book = bookRepository.findById(idBook).orElse(null);
		
		UUID idAuthor = UUID.fromString("955fac97-6c64-4ce5-b5f1-3a082d903f21");
		Author author = authorRepository.findById(idAuthor).orElse(null); 
		
		book.setAuthor(author);
		
		bookRepository.save(book);
	}
	
	 @Test
	 void delete(){
	    UUID id = UUID.fromString("9e9cc135-7923-4149-a46b-a11e98af375d");
	    bookRepository.deleteById(id);
	 }	
	
	@Test
	void findByTitle() {
		List<Book> books = bookRepository.findByTitle("MIsterio da cabana");
		books.forEach(System.out::println);
	}
	
	@Test
	void findByIsbn() {
		Optional<Book> books = bookRepository.findByIsbn("90887-84874");
		books.ifPresent(System.out::println);
	}
	
	@Test
	void findByTitleAndPrice() {
		 var price = BigDecimal.valueOf(250.00);
		List<Book> books = bookRepository.findByTitleAndPrice("MIsterio da cabana", price);
		books.forEach(System.out::println);
	}
	
	@Test
	void listBooksSortedByTitleAndPrice() {
		List<Book> books = bookRepository.listBooksSortedByTitleAndPrice();
		books.forEach(System.out::println);
	}
	
	@Test
	void findByGender() {
		List<Book> books = bookRepository.findByGender(GenderBook.MYSTERY, "price");
		books.forEach(System.out::println);
	}
	
	@Test
	void findByGenderPositionalParameters() {
		List<Book> books = bookRepository.findByGeneroPositionalParameters(GenderBook.MYSTERY, "price");
		books.forEach(System.out::println);
	}
	
	@Test
	void deleteByGender() {
		bookRepository.deleteByGender(GenderBook.SCIENCE);
	}
	
	@Test
	void updateDatePublication() {
		bookRepository.updateDatePublication(LocalDate.of(2000, 1, 1));
	}
	
}

	
