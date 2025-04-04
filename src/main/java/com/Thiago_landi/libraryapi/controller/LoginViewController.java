package com.Thiago_landi.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // é pra paginas web
public class LoginViewController {

	@GetMapping("/login")
	public String pageLogin() {
		return "login";
	}
}
