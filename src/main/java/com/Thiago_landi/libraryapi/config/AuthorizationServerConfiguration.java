package com.Thiago_landi.libraryapi.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {

	@Bean
	@Order(1)//define que essa cadeia de filtros tem prioridade alta (executada antes de outras)
	public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception{
		
		// aplica a configuração padrão de um Authorization Server do Spring
		http.with(OAuth2AuthorizationServerConfigurer.authorizationServer(), Customizer.withDefaults());
		
		//Isso permite que além de tokens, o servidor envie informações sobre o usuário autenticado (via um ID Token)
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
				.oidc(Customizer.withDefaults());
		
		//permite que a aplicação aceite e valide tokens JWT recebidos de clientes (como APIs ou frontends)
		//Isso é necessário quando outras aplicações consomem a API usando tokens de acesso
		// resumindo essa linha habilita o usuario por meio do jwt
		
		
		// identifica que o login deve ser feito no /login(que é o login personalizado)
		http.formLogin(configurer -> configurer.loginPage("/login"));
		
		return http
		        .securityMatcher(new AntPathRequestMatcher("/oauth2/**")) // <- ESSA LINHA É FUNDAMENTAL!
		        .build();
	}
	
	
	// diz Como os tokens vão funcionar
	@Bean
	public TokenSettings tokenSettings() {
		return TokenSettings.builder()
				.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
				.accessTokenTimeToLive(Duration.ofMinutes(60))
				.build();
	}
	
	//Como o cliente vai se autenticar e interagir com o usuário
	@Bean
	public ClientSettings clientSettings() {
		return ClientSettings.builder()
				.requireAuthorizationConsent(false)// tela de consentimento sobre permissão de acesso as informações do usuario(como ta false não vai ter ela)
				.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	public JWKSource<SecurityContext> jwkSource() throws Exception{
		RSAKey rsaKey = generateKeyRSA();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}


	private RSAKey generateKeyRSA() throws Exception{
		//gerador de pares de chave RSA
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		
		RSAPublicKey keyPublic = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey keyPrivate = (RSAPrivateKey) keyPair.getPrivate();
		
		return new RSAKey
				.Builder(keyPublic)
				.privateKey(keyPrivate)
				.keyID(UUID.randomUUID().toString())
				.build();
	}
	
	
	//Validar a assinatura do token (pra garantir que não foi alterado),Ler o conteúdo do 
	//token (como user ID, roles, etc),Passar essas informações pro Spring Security 
	//usar na autorização.
	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}
}
