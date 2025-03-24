package com.Thiago_landi.libraryapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thiago_landi.libraryapi.model.Author;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
	
	List<Author> findByName(String name);
	List<Author> findByNationality(String nationality);
	List<Author> findByNameAndNationality(String name, String nationality);
}
