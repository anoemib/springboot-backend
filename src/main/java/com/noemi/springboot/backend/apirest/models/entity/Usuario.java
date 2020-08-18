package com.noemi.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {    //Serializable es para que pueda convertirse en Json.

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id; 

@Column(unique=true, length=20)
private String username;

@Column(length=60)
private String password; 


private String nombre; 

private String apellido;

@Column(unique = true)
private String email;


public String getNombre() {
	return nombre;
}


public void setNombre(String nombre) {
	this.nombre = nombre;
}


public String getApellido() {
	return apellido;
}


public void setApellido(String apellido) {
	this.apellido = apellido;
}


public String getEmail() {
	return email;
}


public void setEmail(String email) {
	this.email = email;
}


private Boolean enabled;

//@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL) 
/*El cascade, hace que si se elimine el usuario, tambien se eliminan los roles asignados (tabla intermedia).  Además si 
 * creamos los roles del usuario, va a crear automáticamente la tabla rol_usuario.
 */
/*
 * Esto es como ejemplo si queeremos cambiar los nombres de la tabla.
 * @JoinTable(name="users_authorities", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"),
 * uniqueConstraints={@UniqueConstraints(columnNames={"usuario_id", "role_id"})})
 * 
 */
//private List<Role> roles; 


@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
@JoinTable(name="usuarios_roles", joinColumns= @JoinColumn(name="usuario_id"),
inverseJoinColumns=@JoinColumn(name="role_id"),
uniqueConstraints= {@UniqueConstraint(columnNames= {"usuario_id", "role_id"})})
private List<Role> roles;



public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public String getUsername() {
	return username;
}


public void setUsername(String username) {
	this.username = username;
}


public String getPassword() {
	return password;
}


public void setPassword(String password) {
	this.password = password;
}


public Boolean getEnabled() {
	return enabled;
}


public void setEnabled(Boolean enabled) {
	this.enabled = enabled;
}


public List<Role> getRoles() {
	return roles;
}


public void setRoles(List<Role> roles) {
	this.roles = roles;
}


private static final long serialVersionUID = 1L;


}
