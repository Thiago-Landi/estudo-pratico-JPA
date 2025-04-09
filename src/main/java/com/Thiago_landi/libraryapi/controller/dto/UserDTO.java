package com.Thiago_landi.libraryapi.controller.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank(message = "campo obrigatorio")
		String login, 
        @NotBlank(message = "campo obrigatorio")
		String password, 
		@Email(message ="Email invalido")
        @NotBlank(message = "campo obrigatorio")
		String email, 
		List<String> roles) {

}
