package com.Thiago_landi.libraryapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Thiago_landi.libraryapi.controller.dto.UserDTO;
import com.Thiago_landi.libraryapi.controller.mappers.UserMapper;
import com.Thiago_landi.libraryapi.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserMapper mapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> save(@RequestBody UserDTO dto){
		var user = mapper.toEntity(dto);
		service.save(user);
		return ResponseEntity.ok().build();
	}
}
