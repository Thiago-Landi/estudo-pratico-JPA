package com.Thiago_landi.libraryapi.controller;

import java.net.URI;
import java.util.List;
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

import com.Thiago_landi.libraryapi.controller.dto.RegisterBookDTO;
import com.Thiago_landi.libraryapi.controller.dto.SearchBookDTO;
import com.Thiago_landi.libraryapi.controller.mappers.BookMapper;
import com.Thiago_landi.libraryapi.model.Book;
import com.Thiago_landi.libraryapi.model.GenderBook;
import com.Thiago_landi.libraryapi.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("books")
public class BookController implements GenericController {

	@Autowired
	private BookService service;
	
	@Autowired
	private BookMapper mapper;
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid RegisterBookDTO dto){
		Book book = mapper.toEntity(dto);
		service.save(book);
			
		URI location = generateHeaderlocation(book.getId());
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<SearchBookDTO> findById(@PathVariable("id") String id ){
		return service.findById(UUID.fromString(id))
			   .map(Book -> {
				   var dto = mapper.toDTO(Book);
				   return ResponseEntity.ok(dto);
			   }).orElseGet( () -> ResponseEntity.notFound().build() );
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") String id){
		return service.findById(UUID.fromString(id))
				.map(book -> {
					service.delete(book);
					return ResponseEntity.noContent().build();
				}).orElseGet( () -> ResponseEntity.notFound().build());
	}
	
	@GetMapping
	public ResponseEntity<List<SearchBookDTO>> search(
			@RequestParam(value = "isbn", required = false)
			String isbn, 
			@RequestParam(value = "title", required = false)
			String title, 
			@RequestParam(value = "nameAuthor", required = false)
			String nameAuthor, 
			@RequestParam(value = "gender", required = false)
			GenderBook gender, 
			@RequestParam(value = "yearPublication", required = false)
			Integer yearPublication) {
		
		var result = service.search(isbn, title, nameAuthor, gender, yearPublication);
		var list = result.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(list);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Void> update(
			@PathVariable("id") String id, @RequestBody @Valid RegisterBookDTO dto){
		var idBook = UUID.fromString(id);
		
		if(service.findById(idBook).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		service.update(idBook, dto);
		return ResponseEntity.noContent().build();
	}
	
	
}
