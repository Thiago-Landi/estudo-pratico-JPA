package com.Thiago_landi.libraryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Thiago_landi.libraryapi.controller.dto.AuthorDTO;
import com.Thiago_landi.libraryapi.controller.mappers.AuthorMapper;
import com.Thiago_landi.libraryapi.model.Author;
import com.Thiago_landi.libraryapi.service.AuthorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/authors")
public class AuthorController implements GenericController {

	@Autowired
	private AuthorService service;
	
	@Autowired
	private AuthorMapper mapper;
	
	@PostMapping
	@PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<Object> save(@RequestBody @Valid AuthorDTO dto) {
		
		Author authorModel = mapper.toEntity(dto);
		service.save(authorModel);
			
		// esse codigo constroi a url para acessar o author criado (URL:http://localhost:8080/authors/{id}
		URI location = generateHeaderlocation(authorModel.getId());
			
		return ResponseEntity.created(location).build();
		
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
	public ResponseEntity<AuthorDTO> findById(@PathVariable("id") String id) {
		var idAuthor = UUID.fromString(id);
		
		return service
					.findById(idAuthor)
					.map(author -> { 
						 AuthorDTO dto = mapper.toDTO(author);
						 return ResponseEntity.ok(dto);
					}).orElseGet( () -> ResponseEntity.notFound().build() );
	
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<Object> delete(@PathVariable("id") String id) {
		
		var idAuthor = UUID.fromString(id);
		Optional<Author> author = service.findById(idAuthor);
			
		if(author.isEmpty()) {
				return ResponseEntity.notFound().build();
		}
			
		service.delete(author.get());
		return ResponseEntity.noContent().build();
			
		
	}
	
	// esse codigo buscar de acordo ao nome ou a nacionalidade ou os 2
	@GetMapping
	@PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
	public ResponseEntity<List<AuthorDTO>> search(
			@RequestParam(value = "name", required = false) String name, // o required diz que não é obrigado a passar os paramentros, passando um ou o outro ou os 2 vai funcionar
			@RequestParam(value = "nationality", required = false) String nationality) {
		
		List<Author> listAuthor = service.searchByExample(name, nationality);
		List<AuthorDTO> dto = listAuthor.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<Object> update(
			@PathVariable("id") String id, @RequestBody @Valid AuthorDTO dto){
		var idAuthor = UUID.fromString(id);
			
		
		if(service.findById(idAuthor).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
	
		service.update(idAuthor, dto);
		return ResponseEntity.noContent().build();	
	}
}
	
