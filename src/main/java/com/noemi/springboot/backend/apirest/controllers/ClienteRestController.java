package com.noemi.springboot.backend.apirest.controllers;

//import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Date;  Se elimina ya que se incorporó la fecha en la clase de persistencia. 
import java.util.List;
import java.util.Map;
//import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page; //se incorpora la clase tipo page que tiene datos de paginacion
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.noemi.springboot.backend.apirest.models.entity.Cliente;
import com.noemi.springboot.backend.apirest.models.entity.Region;
import com.noemi.springboot.backend.apirest.models.services.IClienteService;
import com.noemi.springboot.backend.apirest.models.services.IUploadFileService;

@CrossOrigin(origins = { "http://localhost:4200" }) // Se define el CORS, es decir de donde pueden venir las peticiones.  Es solo esto para Backend sin seguridad.


@RestController // anotation para dar a entender a Spring el comportamiento de la clase como un
				// controlador del tipo Rest
@RequestMapping("/api") // Se crea el endpoint REST del tipo API.
public class ClienteRestController {
	@Autowired // inyeccion del clienteService que es el modelo donde se están procesando los
				// datos. Con la clase controller los podemos disponer.
	private IClienteService clienteservice; //se inyecta el cliente service
	
	@Autowired
	private IUploadFileService uploadService;
	
//	private final Logger log = LoggerFactory.getLogger(ClienteRestController.class); //se declara log para imprimir por terminal
	
	
	
	@GetMapping("/clientes") // mapeo url del endpoint rest. tipo get
	public List<Cliente> index() {
		return clienteservice.findAll();

	}
	
	@GetMapping("/clientes/page/{page}") // mapeo url del endpoint rest. tipo get
	public Page<Cliente> index(@PathVariable Integer page) { //se incorpora el paginador se retorna un tipo Page
	return clienteservice.findAll(PageRequest.of(page, 4) );

	}

	@Secured(value = { "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/clientes/{id}") // tipo get

	// @ResponseStatus(HttpStatus.OK) //Se define respuesta del httpstatus, del
	// estado http de la petición que se haga. En segunda instancia se comenta ya
	// que se maneja el error a traves de ResponseEntity
	// public Cliente show(@PathVariable Long id){ //recibe un dato en formato long
	// desde una petición get
	public ResponseEntity<?> show(@PathVariable Long id) { // se cambiar el tipo de respuesta para manejar errores. Ya
															// no se retorna una clase cliente. si no algo genérico
															// utilizando la clase Spring.
		// return clienteservice.FindById(id); //se cambiar el retorno para manejar
		// errores

		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();

		try {
			cliente = clienteservice.FindById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a la base de datos"); // Se crea el mensaje a String
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); // el retorno es
																										// un tipo map
																										// con la
																										// respuesta y
																										// el error 404.

		}

		if (cliente == null) { // si no encuentra al cliente
			response.put("mensaje", "El cliente ID:".concat(id.toString().concat(" no existe en la base de datos!"))); // Se
																														// crea
																														// el
																														// mensaje
																														// a
																														// String
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); // 404 el retorno es un tipo map
																							// con la respuesta y el
																							// error 404.
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	@Secured(value = { "ROLE_ADMIN" })
	@PostMapping("/clientes") // tipo post
	// @ResponseStatus(HttpStatus.CREATED)
	// public Cliente create (@RequestBody Cliente cliente) { //recibe un objeto
	// cliente en formato json. desde un post
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {  //para la validacion se agregó el anotation @Valid de Javax y el parámetro Binding result de Spring FW
		// cliente.setCreateAt(new Date()); Esto se cambió y se dejo en la clase de
		// persistencia, en el metodo prePersist. Con el anotation Prepersist
		// return clienteservice.save(cliente); Se cambio para retornar el
		// ResponseEntity para manejar errores
		
		/*
		 * 
		 * El binding result valida inicialmente el json. Averiguar con que valida.
		 */
		
		Cliente clienteNew = null; 
		Map<String, Object> response = new HashMap<>();
		
		
		if (result.hasErrors()) {
		
			/* 
			List<String>  errors = new ArrayList<>(); 
			for (FieldError err: result.getFieldErrors()) {
				errors.add("El campo: '" + err.getField() + "' "+ err.getDefaultMessage());
			}
			Lo anterior a través de un array list. En JDK 8, se puede ocupar el API String.
		    Ver abajo. Se array String de String
			*/
		
			List<String>  errors = result.getFieldErrors()
					.stream() 
					.map(err -> "El campo: '" + err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());   //esta fila vuelve a convertir en un array de string los datos
		
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); //Error 400 peticion invalida

		}
		
		
		try {
			clienteNew = clienteservice.save(cliente);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert a la base de datos"); // Se crea el mensaje a String
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); // Error 500 interno de servidor, consulta erronea,
							
		}
		
		//Como opcion no solo vamos a enviar el cliente nuevo en el mensaje, si no
		//un comentario del insert con exito a través del Map 
		
		response.put("mensaje", "El cliente ha sido creado con éxito"); 
		response.put("cliente", clienteNew); 
		return new ResponseEntity<Map>(response, HttpStatus.CREATED);
	}

	
	
	@Secured(value = { "ROLE_ADMIN" })
	@PutMapping("/clientes/{id}") // tipo put, recibe un post y un get
	//@ResponseStatus(HttpStatus.CREATED) Se cambia la respuesta para manejar el error dentro del metodo
	//public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) { // recibe cliente en formato json por
																					// post y el id en formato get.
		
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente,  BindingResult result, @PathVariable Long id) { //el Binding result, debe ir después del valid
		Cliente clienteActual = clienteservice.FindById(id);
		Cliente clienteUpdated = null;
		
		Map<String, Object> response = new HashMap<>();
		
		
		if (result.hasErrors()) {
			//ver caso análogo comentado en el create
			
			List<String>  errors = result.getFieldErrors()
					.stream() 
					.map(err -> "El campo: '" + err.getField() + "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());   //esta fila vuelve a convertir en un array de string los datos
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); //Error peticion invalida

		}
		
		
		if (clienteActual == null) { 
			response.put("mensaje", "Error: No se pudo editar, el cliente ID:".concat(id.toString().concat(" no existe en la base de datos!"))); 
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		try {
			
			
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setEmail(cliente.getEmail());
		clienteActual.setCreateAt(cliente.getCreateAt());
		clienteActual.setRegion(cliente.getRegion());

		clienteUpdated = clienteservice.save(clienteActual);
		 
		}catch (DataAccessException e) {
			
			response.put("mensaje", "Error al actualizar el cliente en la base de datos"); // Se crea el mensaje a String
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); // el retorno es
		}
		
		response.put("mensaje", "El cliente ha sido actualizado con éxito"); 
		response.put("cliente", clienteUpdated);
		 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	
	@Secured(value = { "ROLE_ADMIN" })
	@DeleteMapping("/clientes/{id}") // es un get con nombre específico delete
//	@ResponseStatus(HttpStatus.NO_CONTENT)
	//public void delete(@PathVariable Long id) {
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			Cliente clienteAnterior = clienteservice.FindById(id);  //se incorpora el eliminar la imagen del usuario, como prueba.
			String nombreFotoAnterior = clienteAnterior.getFoto();
			
			uploadService.eliminar(nombreFotoAnterior); 
			
			/* 
			 * SE ELIMINA YA QUE SE REEMPLAZA POR UPLOADSERVICE
			if (nombreFotoAnterior != null && nombreFotoAnterior.length()>0) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior =  rutaFotoAnterior.toFile();
				if (archivoFotoAnterior.exists() == true && archivoFotoAnterior.canRead() == true) {
					archivoFotoAnterior.delete();
				}
			}   */ 
			
			
			
		clienteservice.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente en la base de datos"); // Se crea el mensaje a String
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); // el retorno es
			
		}
		
		response.put("mensaje", "El cliente ha sido eliminado con éxito"); 
		
		 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
	
	//creación del metodo para carga de imagen adjunta al formulario. Se hace de forma separada al update y create como una buena práctica 
	@Secured(value = { "ROLE_ADMIN", "ROLE_USER" })  //arroja un error 403, que se maneja desde angular.
	@PostMapping("/clientes/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		Cliente cliente = clienteservice.FindById(id); 
		
		
		if (!archivo.isEmpty()) {
			/*String nombreArchivo = UUID.randomUUID().toString() + " " + archivo.getOriginalFilename().replace(" ", "");
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath(); //Posición relativa del archivo respecto al proeycto. Si está fuera se debe poner la ruta exacta (Ej:C:/carpeta/carpeta_proyecto)
			
			log.info(rutaArchivo.toString()); //se imprime el log
			LO ANTERIOR SE ELIMINA YA QUE SE IMPLEMENTA EN LA CLASE UPLOAD SERVICE
			*/
			
			String nombreArchivo = null;
			
			try {
				//Files.copy(archivo.getInputStream(), rutaArchivo);  SE ELIMINA YA QUE SE LLAMA A LA CLASE UPLOAD SERVICE
			nombreArchivo = uploadService.Copiar(archivo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
		//En caso de falla de la subida se ocupa el catch, el mensaje es algo más detallado para listar en angular.
		response.put("mensaje", "Error al subir imagen del cliente ");
		response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));	
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
			}
			//NOTA:EL TAMAÑO MÁXIMO SE PUEDE CONFIGURAR EN APLICATION PROPERTIES.
			
			String nombreFotoAnterior = cliente.getFoto();
			
			
		/*	
		 *  SE ELIMINA YA QUE SE REEMPLAZA POR EL UPLOAD SERVICE
		 * if (nombreFotoAnterior != null && nombreFotoAnterior.length()>0) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior =  rutaFotoAnterior.toFile();
				if (archivoFotoAnterior.exists() == true && archivoFotoAnterior.canRead() == true) {
					archivoFotoAnterior.delete();
				} 
				}*/
			
			uploadService.eliminar(nombreFotoAnterior);
			
			cliente.setFoto(nombreArchivo);
			clienteservice.save(cliente);
			
			response.put("cliente", cliente); 
			response.put("mensaje", "Has subido correctamente la imagen " + nombreArchivo);
			
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); //respuesta desde el backend
	}
	
	@GetMapping("/uploads/img/{nombreFoto:.+}") //nombreFoto va a incluir la extensión por lo que se explicita con expresión regular en el GetMapping
	public ResponseEntity<Resource> verFoto (@PathVariable String nombreFoto){
		
		
		
		
		/* 
		 * SE ELIMNA YA QUE SE OCUPA EL UPLOAD SERVICE
		 * Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		log.info(rutaArchivo.toString());
		
		Resource recurso = null;
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (!recurso.exists() && !recurso.isReadable()) { //si el recurso existe y es leible
			
			 rutaArchivo = Paths.get("src/main/resources/static/images").resolve("no-usuario.png").toAbsolutePath();
			
			try {
				recurso = new UrlResource(rutaArchivo.toUri());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			log.error("Error no se puede cargar la imagen: " + nombreFoto); 
		}  */ 
		
		Resource recurso = null;
		
		try {
			recurso = uploadService.Cargar(nombreFoto);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpHeaders cabecera = new HttpHeaders(); 
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
		return new ResponseEntity<Resource> (recurso, cabecera, HttpStatus.OK);
	}
	
	
	@Secured(value = { "ROLE_ADMIN" })
	@GetMapping("/clientes/regiones") // mapeo url del endpoint rest. tipo get. Se optiene regiones.
	public List<Region> listarRegiones() {
		return clienteservice.findAllRegiones();

	}
	
	
	
	
}
