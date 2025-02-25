package com.Thiago_landi.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thiago_landi.libraryapi.model.Author;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

}
