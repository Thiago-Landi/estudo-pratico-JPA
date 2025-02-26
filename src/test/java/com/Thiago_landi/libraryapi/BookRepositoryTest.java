package com.Thiago_landi.libraryapi;

import java.math.BigDecimal;
import java.time.LocalDate;
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
	
	/*@Test
	void save() {
		Book book = new Book();
		
		book.setIsbn("90887-84874");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(GenderBook.FICTION);
        book.setTitle("Outro Livro");
        book.setDatePublication(LocalDate.of(1980, 1, 2));
        
        Author author = authorRepository.findById(UUID.fromString("ebd79aa6-6b91-49cb-8627-0df4b8de1eaf"))
        		.orElse(null);
        
        book.setAuthor(author);
        bookRepository.save(book);
	}*/
	
	/*@Test
	void updateBookAuthor() {
		UUID idBook = UUID.fromString("9e9cc135-7923-4149-a46b-a11e98af375d");
		Book book = bookRepository.findById(idBook).orElse(null);
		
		UUID idAuthor = UUID.fromString("955fac97-6c64-4ce5-b5f1-3a082d903f21");
		Author author = authorRepository.findById(idAuthor).orElse(null); 
		
		book.setAuthor(author);
		
		bookRepository.save(book);
	}*/
	
	 @Test
	    void delete(){
	        UUID id = UUID.fromString("9e9cc135-7923-4149-a46b-a11e98af375d");
	        bookRepository.deleteById(id);
	    }
}

	
