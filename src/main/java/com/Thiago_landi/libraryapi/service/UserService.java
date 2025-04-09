package com.Thiago_landi.libraryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Thiago_landi.libraryapi.model.UserClass;
import com.Thiago_landi.libraryapi.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder enconder;
	
	public void save(UserClass user) {
		var password = user.getPassword();
		user.setPassword(enconder.encode(password));
		repository.save(user);
	}
	
	public UserClass findByLogin(String login) {
		return repository.findByLogin(login);
	}
	
	public UserClass findByEmail(String email) {
		return repository.findByEmail(email);
	}
}
