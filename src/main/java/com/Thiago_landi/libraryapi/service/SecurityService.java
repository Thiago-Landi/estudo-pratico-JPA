package com.Thiago_landi.libraryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.Thiago_landi.libraryapi.model.UserClass;

@Component
public class SecurityService {

	@Autowired
	private UserService userService;
	
	public UserClass getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String login = userDetails.getUsername();
		return userService.findByLogin(login);				
	}
}
