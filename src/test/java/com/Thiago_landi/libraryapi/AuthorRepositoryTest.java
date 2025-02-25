package com.Thiago_landi.libraryapi;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Thiago_landi.libraryapi.model.Author;
import com.Thiago_landi.libraryapi.repository.AuthorRepository;

@SpringBootTest
public class AuthorRepositoryTest {

	@Autowired
    AuthorRepository repository;

    @Test
    public void salvarTest(){
        Author author = new Author();
        author.setName("Maria");
        author.setNationality("Brasileira");
        author.setDateBirth(LocalDate.of(1951, 1, 31));

        var autorSalvo = repository.save(author);
        System.out.println("Autor Salvo: " + autorSalvo);
    }

   @Test
    public void atualizarTest(){
        var id = UUID.fromString("d2df2e0d-482f-468e-8947-272cd2825916");

        Optional<Author> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){

            Author autorEncontrado =  possivelAutor.get();
            System.out.println("Dados do Autor:");
            System.out.println(autorEncontrado);

            autorEncontrado.setNationality("francesa");

            repository.save(autorEncontrado);

        }
    }
	
   @Test
   public void listarTest(){
       List<Author> lista = repository.findAll();
       lista.forEach(System.out::println);
   }
   
   @Test
   public void countTest(){
       System.out.println("Contagem de autores: " + repository.count());
   }
   
   
   @Test
   public void deletePorIdTest(){
       var id = UUID.fromString("d2df2e0d-482f-468e-8947-272cd2825916");
       repository.deleteById(id);
   }
   
}
