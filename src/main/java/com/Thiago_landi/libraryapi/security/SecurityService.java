package com.Thiago_landi.libraryapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.Thiago_landi.libraryapi.model.UserClass;
import com.Thiago_landi.libraryapi.service.UserService;

@Component
public class SecurityService {

	@Autowired
	private UserService userService;
	
	//Retorna o usuário atualmente logado. Se o usuário estiver autenticado por meio do CustomAuthentication, o método retorna o objeto Usuario associado. Caso contrário, retorna null.
	public UserClass getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verifica se o objeto de autenticação é uma instância de CustomAuthentication
	    // Se for, isso indica que um usuário foi autenticado com sucesso
	    if(authentication instanceof CustomAuthentication customAuth){
	         return customAuth.getUser();
	    }

	    return null;
	}        
}
