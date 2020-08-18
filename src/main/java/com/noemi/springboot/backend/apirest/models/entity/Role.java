package com.noemi.springboot.backend.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, length = 20) // tener en cuenta que con Spring security, todos los roles comienzan con "Rol_"
	private String nombre;

	/*	   Esto es como ejemplo para hacer las consultas de forma bidireccional
	@ManyToMany(mappedBy="roles")
	private List<Usuario> usuario;
	*/
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
