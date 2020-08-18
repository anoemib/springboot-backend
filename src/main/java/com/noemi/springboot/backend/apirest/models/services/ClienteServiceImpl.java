package com.noemi.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.noemi.springboot.backend.apirest.models.dao.IClienteDao;
import com.noemi.springboot.backend.apirest.models.entity.Cliente;
import com.noemi.springboot.backend.apirest.models.entity.Region;

@Service  //anotation para dejar claro la Framework Spring que es una clase de tipo servicio.
public class ClienteServiceImpl implements IClienteService {

	
	
	@Autowired  //Inyeccion de dependencias en Spring, se inyecta la clase clienteDao, con la conexión a la base de datos. 
	private IClienteDao clienteDao;   //inyección de un componente en la clase actual. clienteDao se puede inyectar en cualquier lado.
	
	@Override
	@Transactional(readOnly = true)  //permite manejar transacciones en el metodo, si es consulta de lectura debe ser true. Se puede omitir, se hace de forma explicita
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDao.findAll();
	}	
	
	
	@Override
	@Transactional(readOnly = true)  //permite manejar transacciones en el metodo, si es consulta de lectura debe ser true. Se puede omitir, se hace de forma explicita
	public Page<Cliente> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return clienteDao.findAll(pageable);
	}
	

	@Override
	@Transactional(readOnly = true)
	public Cliente FindById(Long id) {
		// TODO Auto-generated method stub
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		// TODO Auto-generated method stub
		return clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		clienteDao.deleteById(id);
		
	}


	@Override
	@Transactional(readOnly = true)  //Se declara transaccional, ya que hace consultas a la base de datos. Es ReadOnly, ya que solo lee. No hace cambios. 
	public List<Region> findAllRegiones() {
		// TODO Auto-generated method stub
		return clienteDao.findAllRegiones();
	}

	
	
	
	

}
