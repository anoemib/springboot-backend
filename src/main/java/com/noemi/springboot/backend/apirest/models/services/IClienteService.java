package com.noemi.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.data.domain.Page; //se incorpora las clase Page que tiene atributos para manejar las paginas
import org.springframework.data.domain.Pageable;//se incorpora la interfaz paginador.

import com.noemi.springboot.backend.apirest.models.entity.Cliente;
import com.noemi.springboot.backend.apirest.models.entity.Region;

public interface IClienteService {  //solo contiene el contrato de implementación para ser implementados por la clase. 
	
	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public Cliente FindById(Long id);

	public Cliente save(Cliente cliente);
	
	public void delete(Long id);
	
	public List<Region> findAllRegiones();
	
	

}
