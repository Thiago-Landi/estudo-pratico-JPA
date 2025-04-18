package com.Thiago_landi.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thiago_landi.libraryapi.model.UserClass;

public interface UserRepository extends JpaRepository<UserClass, UUID>{

	UserClass findByLogin(String login);

	UserClass findByEmail(String email);
}
