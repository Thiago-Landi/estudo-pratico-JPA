package com.Thiago_landi.libraryapi;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Thiago_landi.libraryapi.model.Author;
import com.Thiago_landi.libraryapi.model.Book;
import com.Thiago_landi.libraryapi.repository.AuthorRepository;
import com.Thiago_landi.libraryapi.repository.BookRepository;

@SpringBootTest
public class AuthorRepositoryTest {

	@Autowired
    AuthorRepository authorRepository;
	
	@Autowired
	BookRepository bookRepository;

	
    @Test
    public void salvarTest(){
        Author author = new Author();
        author.setName("Maria");
        author.setNationality("Brasileira");
        author.setDateBirth(LocalDate.of(1951, 1, 31));

        var autorSalvo = authorRepository.save(author);
        System.out.println("Autor Salvo: " + autorSalvo);
    }

   @Test
    public void atualizarTest(){
        var id = UUID.fromString("955fac97-6c64-4ce5-b5f1-3a082d903f21");

        Optional<Author> possivelAutor = authorRepository.findById(id);

        if(possivelAutor.isPresent()){

            Author autorEncontrado =  possivelAutor.get();
            System.out.println("Dados do Autor:");
            System.out.println(autorEncontrado);

            autorEncontrado.setName("Joao");

            authorRepository.save(autorEncontrado);

        }
    }
	
   
   @Test
   public void listarTest(){
       List<Author> lista = authorRepository.findAll();
       lista.forEach(System.out::println);
   }
   
   @Test
   public void countTest(){
       System.out.println("Contagem de autores: " + authorRepository.count());
   }
   
   
   @Test
   public void deletePorIdTest(){
       var id = UUID.fromString("d2df2e0d-482f-468e-8947-272cd2825916");
       authorRepository.deleteById(id);
   }
	
	@Test
	void bookFindByAuthor() {
		var id = UUID.fromString("955fac97-6c64-4ce5-b5f1-3a082d903f21");
		Author author = authorRepository.findById(id).orElse(null);
		
		List<Book> books = bookRepository.findByAuthor(author);
		author.setBooks(books);
		
		author.getBooks().forEach(System.out::println);
	}
	
}
