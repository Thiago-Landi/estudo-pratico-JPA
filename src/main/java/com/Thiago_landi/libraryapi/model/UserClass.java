package com.Thiago_landi.libraryapi.model;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_tb")
@Data
public class UserClass {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column
    private String login;
	
	@Column(name = "password_")
	private String password;
	
	@Column
	private String email;
	
	@Type(ListArrayType.class)
    @Column(name = "roles", columnDefinition = "varchar[]")
	private List<String> roles;
}
