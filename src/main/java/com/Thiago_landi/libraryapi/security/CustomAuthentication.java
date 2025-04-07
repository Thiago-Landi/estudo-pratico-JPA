package com.Thiago_landi.libraryapi.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.Thiago_landi.libraryapi.model.UserClass;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomAuthentication implements Authentication {

	private final UserClass user;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.user
				.getRoles().stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return user;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		
	}
	
	@Override
		public String getName() {
			return user.getLogin();
		}

	public UserClass getUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
