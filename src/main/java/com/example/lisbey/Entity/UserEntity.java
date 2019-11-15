package com.example.lisbey.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.*;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity implements Serializable{
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "El campo name es requerido")
	private String name;
	
	@NotEmpty(message = "El campo lastName es requerido")
	private String lastName;

	private  String age;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
