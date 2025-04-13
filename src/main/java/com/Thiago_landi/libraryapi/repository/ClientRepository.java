package com.Thiago_landi.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Thiago_landi.libraryapi.model.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {

	Client findByClientId(String clientId);

}
