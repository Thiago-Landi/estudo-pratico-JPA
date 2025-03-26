package com.Thiago_landi.libraryapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thiago_landi.libraryapi.model.Author;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
	
	List<Author> findByName(String name);
	List<Author> findByNationality(String nationality);
	List<Author> findByNameAndNationality(String name, String nationality);
	
	Optional<Author> findByNameAndDateBirthAndNationality(
			String name, LocalDate dateBirth, String Nationality);
}
