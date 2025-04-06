package com.Thiago_landi.libraryapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.Thiago_landi.libraryapi.security.CustomUserDetailsService;
import com.Thiago_landi.libraryapi.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http	            
	            .csrf(AbstractHttpConfigurer::disable)// Desativa a proteção CSRF, útil para APIs REST
	            .httpBasic(Customizer.withDefaults())// Habilita a autenticação básica (usuário/senha via pop-up do navegador ou em headers HTTP) 
	            .formLogin(configurer ->{
	            	configurer.loginPage("/login");
	            })
	            .authorizeHttpRequests(authorize -> {// Configura regras de autorização para as requisições HTTP
	            	authorize.requestMatchers("/login/**").permitAll();	 
	            	authorize.requestMatchers(HttpMethod.POST, "/users/**").permitAll();
	            	
	            	authorize.anyRequest().authenticated();// Exige autenticação para qualquer requisição na aplicação
	            })
	            .build();// Constrói e retorna a configuração de segurança
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	public UserDetailsService userDetailsService(UserService userService) {	
		return new CustomUserDetailsService(userService);
	}
}
