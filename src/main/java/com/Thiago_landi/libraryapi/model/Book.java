package com.Thiago_landi.libraryapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "book")
@Getter
@Setter
@ToString(exclude = "author")
@EntityListeners(AuditingEntityListener.class)
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(length = 20, nullable = false)
	private String isbn;
	
	@Column(length = 150, nullable = false )
	private String title;
	
	@Column(name = "date_publication", nullable = false )
	private LocalDate datePublication;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 30, nullable = false)
	private GenderBook gender;
	
	@Column(precision = 18, scale = 2)
	private BigDecimal price;
	
	@ManyToOne
	@JoinColumn(name = "id_author", nullable = false)
	private Author author;
	
	//vai inserir a data da criação automaticamente 
	@CreatedDate
	@Column(name = "date_register")
	private LocalDateTime dateRegister;
		
	//vai atualizar a data automaticamente após ocorrera atualização do author
	@LastModifiedDate
	@Column(name = "date_update")
	private LocalDateTime dateUpdate ;
		
	@Column(name = "id_user")
	private UUID idUser;
	
	public Book() {
	}
}
