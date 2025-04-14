package com.Thiago_landi.libraryapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import com.Thiago_landi.libraryapi.service.ClientService;

@Component
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

	@Autowired
	private ClientService service;
	
	@Autowired
	private TokenSettings tokenSettings;
	
	@Autowired
	private ClientSettings clientSettings;
	
	@Override
	public void save(RegisteredClient registeredClient) {
		
	}

	@Override
	public RegisteredClient findById(String id) {
		return null;
	}

	// retorna o client de acordo ao cliendId da requisição
	//está criando um RegisteredClient dinamicamente, com base nos dados que vêm do banco, 
	//e retornando ele pro Spring Authorization Server.
    //Esse objeto é o que o Spring vai usar pra validar a autenticação do cliente.
	@Override
	public RegisteredClient findByClientId(String clientId) {
		var client = service.findByClient(clientId);
		
		if (client == null) {
	        System.out.println("❌ Cliente não encontrado!");
	        return null;
	    }

	    System.out.println("✅ Cliente encontrado: " + client.getClientId());
		
		return RegisteredClient
				.withId(client.getId().toString())
				.clientId(client.getClientId())
				.clientSecret(client.getClientSecret())
				.redirectUri(client.getRedirectURI())
				.scope(client.getScope())
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				//O usuário é redirecionado para uma tela de login. Após logar, o sistema gera um "authorization code".
				//Esse código é trocado por um token de acesso (access token).O cliente pode usar esse token para acessar a API.
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				// O cliente (ex: microserviço) envia o client_id e o client_secret. O servidor autentica o cliente.
				//Retorna um token de acesso. Esse token é usado pra acessar a API.(sem interaçãocom usuario)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)// é usado quando já tem o token e precisa renovar ele
				.tokenSettings(tokenSettings) // passando o token personalizado
				.clientSettings(clientSettings) // passando Como o cliente vai se autenticar e interagir com o usuário
				.build();
	}

}
