package com.Thiago_landi.libraryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Thiago_landi.libraryapi.model.Client;
import com.Thiago_landi.libraryapi.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Autowired
    private PasswordEncoder encoder;
	
	public Client save(Client client) {
		var passwordEncrypted = encoder.encode(client.getClientSecret());
		client.setClientSecret(passwordEncrypted);
		return repository.save(client);
	}
	
	public Client findByClient(String clientId) {
		return repository.findByClientId(clientId);
	}
}
