package com.Thiago_landi.libraryapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http	            
	            .csrf(AbstractHttpConfigurer::disable)// Desativa a proteção CSRF, útil para APIs REST
	            .httpBasic(Customizer.withDefaults())// Habilita a autenticação básica (usuário/senha via pop-up do navegador ou em headers HTTP) 
	            .formLogin(configurer ->{
	            	configurer.loginPage("/login").permitAll();
	            })
	            .authorizeHttpRequests(authorize -> {// Configura regras de autorização para as requisições HTTP
	                authorize.anyRequest().authenticated();// Exige autenticação para qualquer requisição na aplicação
	            })
	            .build();// Constrói e retorna a configuração de segurança
	}
}
