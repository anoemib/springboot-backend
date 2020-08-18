package com.noemi.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.noemi.springboot.backend.apirest.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository <Usuario, Long>{
	
	public Usuario findByUsername(String username);   //tener en cuenta que es una convención el nombre. Find By ... donde los tres puntos son el nombre del campo a buscar.
	
	//public Usuario findByUsernameAndEmail(string, string)  // otro ejemplo, se puede ocupar el and.
	
	/* otro ejemplo 
	@Query("select u from Usuario u where u.username?=1 and u.otro=2")
	public Usuario findByUsername(String username);    
	  */

}
