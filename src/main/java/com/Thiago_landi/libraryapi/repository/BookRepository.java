package com.Thiago_landi.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thiago_landi.libraryapi.model.Book;

public interface BookRepository extends JpaRepository<Book, UUID>{

}
