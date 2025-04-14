package com.Thiago_landi.libraryapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import com.Thiago_landi.libraryapi.security.JwtCustomAuthenticationFilter;
import com.Thiago_landi.libraryapi.security.LoginSocialSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

	
	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity http, 
			LoginSocialSuccessHandler successHandler,
			JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter) throws Exception {
	    return http	            
	            .csrf(AbstractHttpConfigurer::disable)// Desativa a proteção CSRF, útil para APIs REST
	            .httpBasic(Customizer.withDefaults())//Ativa o login básico do navegador (usuário/senha via pop-up do navegador ou em headers HTTP) 
	            .formLogin(configurer ->{
	            	configurer.loginPage("/login");//Habilita o formulário de login (aquele que você cria com um HTML)
	            })
	            .authorizeHttpRequests(authorize -> {// Configura regras de autorização para as requisições HTTP
	            	authorize.requestMatchers("/login/**").permitAll();	 
	            	authorize.requestMatchers(HttpMethod.POST, "/users/**").permitAll();
	            	
	            	authorize.anyRequest().authenticated();// Exige autenticação para qualquer requisição na aplicação
	            })
	            .oauth2Login(oauth2 -> {// vai se autenticar via google/oauth2 e vai para uma authentication personalizada
	            	oauth2
	            		.loginPage("/login")
	            		.successHandler(successHandler);
	            })
	            //essa linha habilita o usuario por meio do jwt
	            .oauth2ResourceServer(oauth2RS -> oauth2RS.jwt(Customizer.withDefaults()))
	            .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class )
	            .build();// Constrói e retorna a configuração de segurança
	}
	
	// Por padrão, o Spring Security adiciona o prefixo "ROLE_" às authorities. Ao passar uma string vazia (""), você está removendo esse prefixo.
	@Bean
	public GrantedAuthorityDefaults grantedAuthorityDefaults() {
		return new GrantedAuthorityDefaults("");
	}
	
	
	// faz com que o Spring saiba interpretar o token JWT recebido, e entenda corretamente 
	//quais permissões (roles ou scopes) o usuário tem.
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
		authoritiesConverter.setAuthorityPrefix(""); //retira o prefixo padrão
		
		var converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
		return converter;
	}
	

}
