package com.Thiago_landi.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.Thiago_landi.libraryapi.model.Author;
import com.Thiago_landi.libraryapi.model.Book;
import com.Thiago_landi.libraryapi.model.GenderBook;

public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book>{

	// Query Method
    // select * from book where id_author = id
	List<Book> findByAuthor(Author author);
	
    // select * from book where title = title
	List<Book> findByTitle(String title);
	
    // select * from book where isbn = ?
	Optional<Book> findByIsbn (String isbn);
	
    // select * from book where title = ? and price = ?	
	List<Book> findByTitleAndPrice(String title, BigDecimal price);
	
    // select * from book where title = ? or isbn = ?
	List<Book> findByTitleOrIsbn(String title, String isbn);
	
	// JPQL -> referencia as entidades e as propriedades
	
	@Query(" select b from Book as b order by b.title, b.price ")
	List<Book> listBooksSortedByTitleAndPrice();
	
	//Parametros nomeados
	
	@Query("select  b from Book b where b.gender = :gender order by :ParamOrder")
	List<Book> findByGender(@Param("gender") GenderBook gender, 
							@Param("ParamOrder") String paramOrder);
	
    // positional parameters
    @Query("select b from Book b where b.gender = ?1 order by ?2 ")
    List<Book> findByGeneroPositionalParameters(GenderBook generoLivro, String nomePropriedade);

    @Modifying
    @Transactional
    @Query(" delete from Book where gender = ?1 ")
    void deleteByGender(GenderBook gender);
	
    //Não faça um update ou delete sem where, esse coidgo é apenas para mostrar como funciona
	@Modifying
	@Transactional
	@Query(" update Book set datePublication =?1 ")
	void updateDatePublication(LocalDate newDate);
    
    boolean existsByAuthor(Author author);
	}
