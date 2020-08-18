package com.noemi.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository; Se reemplaza por JpaRepository para incorporar paginación
import org.springframework.data.jpa.repository.Query;

import com.noemi.springboot.backend.apirest.models.entity.Cliente;
import com.noemi.springboot.backend.apirest.models.entity.Region; //Se importa el entity Region.s
//DAO es el tipo de clases que hacen las consultas a las bases de datos directamente. 
//las clases service implementan la lógica correspondiente. Son los controladores. 
//public interface IClienteDao extends CrudRepository <Cliente, Long> {  //implementa todas las operaciones básicas en la base de datos a través de Spring Boot extendiendo Crudrepository, ve la documentación de Spring Boot en linea.
//Se agrega la clase que implementa la tabla de la base de datos y el id de la tabla. 
//Ver documantación de Spring para ver los metodos por defecto.	
	
public interface IClienteDao extends JpaRepository <Cliente, Long> {  //se incorpora la paginación, para eso se extiende la clase JPARepository, en desmedro de CrudRepository. JPARepository extiende de PagingandsortingReposityr este de CrudRepository  

	@Query("from Region") //Se personaliza la consulta, Región es la clase entity, no la tabla. Tener en cuenta que esto es opcional.
	public List<Region> findAllRegiones();  //Se incorpora acá la busqueda de la región solo para simplificar en este caso.  También podría región tener su propio DAO. Eso es lo más recomendable.
	/*
	 * 
	 * Tener en cuenta que se debe incorporar posteriormente en el ClienteService
	 */
	
}
