package com.Thiago_landi.libraryapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Thiago_landi.libraryapi.security.CustomAuthentication;

@Controller // é pra paginas web
public class LoginViewController {

	// retorna o login.html para mostrar a pagina de login
	@GetMapping("/login")
	public String pageLogin() {
		return "login"; // arquivo html
	}
	
	@GetMapping("/")
	@ResponseBody
	public String pageHome(Authentication authentication) {
		if(authentication instanceof CustomAuthentication customAuth) {
			System.out.println(customAuth.getUser());
		}
		
		return "Olá " + authentication.getName();
	}
	
	@GetMapping("/authorized")
	@ResponseBody
	public String getAuthorizationCode(@RequestParam("code") String code){
         return "Seu authorization code: " + code;
	}
	
}
