package com.Thiago_landi.libraryapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Thiago_landi.libraryapi.security.CustomAuthentication;

@Controller // é pra paginas web
public class LoginViewController {

	@GetMapping("/login")
	public String pageLogin() {
		return "login";
	}
	
	@GetMapping("/")
	@ResponseBody
	public String pageHome(Authentication authentication) {
		if(authentication instanceof CustomAuthentication customAuth) {
			System.out.println(customAuth.getUser());
		}
		
		return "Olá " + authentication.getName();
	}
	
	
}
