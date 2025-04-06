package com.Thiago_landi.libraryapi.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.Thiago_landi.libraryapi.model.UserClass;
import com.Thiago_landi.libraryapi.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	
	private final UserService userService;
	
	//pega o login enviado no formulário, procura o usuário no banco, 
	//e se ele existir, retorna os dados para o Spring Security usar na autenticação.
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		UserClass user = userService.findByLogin(login);
		
		if(user == null) {
			throw new UsernameNotFoundException("Usuario não encontrado!");
		}
		
		
		
		return User.builder()
				.username(user.getLogin())
				.password(user.getPassword())
				.roles(user.getRoles().toArray(new String[user.getRoles().size()]))
				.build() ;
	}

}
