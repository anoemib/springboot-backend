package com.noemi.springboot.backend.apirest.models.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.noemi.springboot.backend.apirest.models.dao.IUsuarioDao;
import com.noemi.springboot.backend.apirest.models.entity.Usuario;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService { //Se implementa la interfaz, esta tiene una serie de funcionalidades para administrar usuarios desde Spring. Tener en cuenta que se deben cargar las annotations desde Spring.

	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private IUsuarioDao usuarioDao; 
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if (usuario == null) { 
			logger.error("Error en el login: No existe el usuario " + username + " en el sistema!");
			throw new UsernameNotFoundException("Error en el login: No existe el usuario " + username + " en el sistema!"); 
		}
		
		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre())).
				peek(authority -> logger.info("Role :" + authority.getAuthority())).
				collect(Collectors.toList()); 
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsername(String username) {
		// TODO Auto-generated method stub
		return usuarioDao.findByUsername(username);
	} 	
	
	

	
	
	
}
