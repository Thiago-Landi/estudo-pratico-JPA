package com.Thiago_landi.libraryapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.Thiago_landi.libraryapi.model.UserClass;
import com.Thiago_landi.libraryapi.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	// esse codigo faz a autenticação(authentication) do login feito pelo usuario, esse é o 
	//authentication personalizado
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String login = authentication.getName();//em authentication, geteName é Login
		String passwordTyped = authentication.getCredentials().toString();// senha digitada
		
		UserClass userFound = userService.findByLogin(login);
		
		if(userFound == null) {
			throw getUserErrorNotFound();
		}
		
		String passwordEncrypted = userFound.getPassword();// senha do login criptografada no banco de dados
		
		
		boolean passwordEquals = encoder.matches(passwordTyped, passwordEncrypted);//comparando senha digitada pelo cliente com senha do login que tava salvo no banco de dados
		
		
		if(passwordEquals) { // se as senhas forem iguais, então o login foi bem-sucedido e retornara o objeto valido
			return new CustomAuthentication(userFound);
		}
		
		throw getUserErrorNotFound();// caso as senhas sejam diferentes
		}
	
	private UsernameNotFoundException getUserErrorNotFound() {
		return new UsernameNotFoundException("Usuario e/ou senha incorretos");
	}

	//Verifica se a classe de autenticação fornecida (authentication) é compatível com UsernamePasswordAuthenticationToken.
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
	}

}
