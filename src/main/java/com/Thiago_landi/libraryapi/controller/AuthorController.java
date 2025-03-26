package com.Thiago_landi.libraryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Thiago_landi.libraryapi.controller.dto.AuthorDTO;
import com.Thiago_landi.libraryapi.controller.dto.ErrorResponse;
import com.Thiago_landi.libraryapi.exceptions.InvalidOperationException;
import com.Thiago_landi.libraryapi.exceptions.RegistryDuplicateException;
import com.Thiago_landi.libraryapi.model.Author;
import com.Thiago_landi.libraryapi.service.AuthorService;

@RestController
@RequestMapping("/authors")
public class AuthorController {

	@Autowired
	private AuthorService service;
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody AuthorDTO author) {
		try {
			Author authorModel = author.mapForAuthor();
			service.save(authorModel);
			
			// esse codigo constroi a url para acessar o author criado (URL:http://localhost:8080/authors/{id}
			URI location = ServletUriComponentsBuilder
	        .fromCurrentRequest()
	        .path("/{id}")
	        .buildAndExpand(authorModel.getId())
	        .toUri();
			
			return ResponseEntity.created(location).build();
		}catch(RegistryDuplicateException e) {
			var errorDTO = ErrorResponse.conflict(e.getMessage());
			return ResponseEntity.status(errorDTO.status()).body(errorDTO);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<AuthorDTO> findById(@PathVariable("id") String id) {
		var idAuthor = UUID.fromString(id);
		Optional<Author> authorOptional = service.findById(idAuthor);
		if(authorOptional.isPresent()) {
			Author author = authorOptional.get();
			AuthorDTO dto = new AuthorDTO(
					 author.getId(), author.getName(),
					 author.getDateBirth(), author.getNationality());
			
			return ResponseEntity.ok(dto);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") String id) {
		try {
			var idAuthor = UUID.fromString(id);
			Optional<Author> author = service.findById(idAuthor);
			
			if(author.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			service.delete(author.get());
			return ResponseEntity.noContent().build();
			
		}catch(InvalidOperationException e) {
			var errorResponse = ErrorResponse.responseDefault(e.getMessage());
			return ResponseEntity.status(errorResponse.status()).body(errorResponse);
		}
	}
	
	// esse codigo buscar de acordo ao nome ou a nacionalidade ou os 2
	@GetMapping
	public ResponseEntity<List<AuthorDTO>> search(
			@RequestParam(value = "name", required = false) String name, // o required diz que não é obrigado a passar os paramentros, passando um ou o outro ou os 2 vai funcionar
			@RequestParam(value = "nationality", required = false) String nationality) {
		
		List<Author> listAuthor = service.search(name, nationality);
		List<AuthorDTO> dto = listAuthor.stream()
				.map(author -> new AuthorDTO(author.getId(), author.getName(), author.getDateBirth(), author.getNationality()))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Object> update(
			@PathVariable("id") String id, @RequestBody AuthorDTO dto){
		
		try {
			var idAuthor = UUID.fromString(id);
			
			Optional<Author> optional = service.findById(idAuthor);
			if(optional.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			Author authorPostman = dto.mapForAuthor();
			authorPostman.setId(idAuthor);
			
			service.update(idAuthor, authorPostman);
			return ResponseEntity.noContent().build();
			
		} catch(RegistryDuplicateException e) {
			var errorDTO = ErrorResponse.conflict(e.getMessage());
			return ResponseEntity.status(errorDTO.status()).body(errorDTO);
		}
	}
}
	
