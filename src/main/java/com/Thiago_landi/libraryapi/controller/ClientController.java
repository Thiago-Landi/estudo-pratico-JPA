package com.Thiago_landi.libraryapi.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Thiago_landi.libraryapi.model.Client;
import com.Thiago_landi.libraryapi.service.ClientService;

@RestController
@RequestMapping("clients")
public class ClientController implements GenericController{

	@Autowired
	private ClientService service;
	
	@PostMapping
	@PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<Void> save(@RequestBody Client client){
		service.save(client);
		
		URI location = generateHeaderlocation(client.getId());
		
		return ResponseEntity.created(location).build();
	}
}
