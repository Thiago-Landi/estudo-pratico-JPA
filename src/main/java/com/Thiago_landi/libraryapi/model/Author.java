package com.Thiago_landi.libraryapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "author")
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)// ele que permite que as anottations de auditoria que colocam o tempo automaticamente
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(name = "date_birth", nullable = false)
	private LocalDate dateBirth;
	
	@Column(nullable = false, length = 50)
	private String nationality;
	
	@OneToMany(mappedBy = "author")
	private List<Book> books;
	
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
}
