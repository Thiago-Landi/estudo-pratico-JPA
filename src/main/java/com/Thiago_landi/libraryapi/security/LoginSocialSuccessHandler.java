package com.Thiago_landi.libraryapi.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.Thiago_landi.libraryapi.model.UserClass;
import com.Thiago_landi.libraryapi.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//Ele salva a página que o usuário tentou acessar antes do login.
//Após o login com sucesso, ele redireciona automaticamente para essa página
@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
    private static final String PASSWORD_DEFAULT = "321";// apenas para fins de estudo
	private final UserService userService;
	
	// faz a ponte entre o login do Google e o seu sistema real, garantindo que o usuário 
	//esteja cadastrado e reconhecido no backend.
	@Override
		public void onAuthenticationSuccess(
				HttpServletRequest request, 
				HttpServletResponse response,
				Authentication authentication) throws ServletException, IOException {
			
		//o OAuth2AuthenticationToken É usado quando o usuário faz login com Google, Guarda os dados do perfil do usuário
		//Permite acesso as informações no backend
			OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication; 
			OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();
			
			String email = oAuth2User.getAttribute("email");
		
			UserClass user = userService.findByEmail(email);
			
			if(user == null) {
				user = registerUserInDatabase(email);
			}
			
			authentication = new CustomAuthentication(user);
			
			//Essa linha registra no sistema de segurança do Spring que o usuário está logado.
			//Sem ela, mesmo que o login esteja certo, o Spring não vai reconhecer o usuário como autenticado.
			SecurityContextHolder.getContext().setAuthentication(authentication);
		
			super.onAuthenticationSuccess(request, response, authentication);
		}
	
	private UserClass registerUserInDatabase(String email) {
		UserClass user = new UserClass();
		user.setEmail(email);
		
		user.setLogin(loginFromEmail(email));
		
		user.setPassword(PASSWORD_DEFAULT);
		user.setRoles(List.of("OPERADOR"));
		
		userService.save(user);
		return user;
		
	}
	
	private String loginFromEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
