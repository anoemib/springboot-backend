package com.noemi.springboot.backend.apirest.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {
	
	
	private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class); //se declara log para imprimir por terminal	
	
	private final static String DIRECTORIO_UPLOAD = "uploads";  //final quiere decir que no se puede modificar a futuro, y static significa que pertenece a la clase y no al objeto.
	
	
	
	@Override
	public Resource Cargar(String nombreFoto) throws MalformedURLException {
		// TODO Auto-generated method stub
		
		Path rutaArchivo = getPath(nombreFoto); 
		log.info(rutaArchivo.toString());
		
		
	//	try {
		Resource recurso = new UrlResource(rutaArchivo.toUri());
		/* } catch (MalformedURLException e) {
			// TODO Auto-generated catch block    Se comenta ya que la clase arroja una excepción. Esto se maneja desde la clase que llama al metodo. 
			e.printStackTrace();
		}*/ 
		
		if (!recurso.exists() && !recurso.isReadable()) { //si el recurso existe y es leible
			
			 rutaArchivo = Paths.get("src/main/resources/static/images").resolve("no-usuario.png").toAbsolutePath();
			
			//try {
				recurso = new UrlResource(rutaArchivo.toUri());
			/*} catch (MalformedURLException e) {
				// TODO Auto-generated catch block Sin Try y catch, los errores se manejan desde la clase que llama al metodo
				e.printStackTrace();
			}*/ 
			
			
			log.error("Error no se puede cargar la imagen: " + nombreFoto); 
		}
		return recurso;
	}

	@Override
	public String Copiar(MultipartFile archivo) throws IOException {
		// TODO Auto-generated method stub
		
		
		String nombreArchivo = UUID.randomUUID().toString() + " " + archivo.getOriginalFilename().replace(" ", "");
		//Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath(); //Posición relativa del archivo respecto al proeycto. Si está fuera se debe poner la ruta exacta (Ej:C:/carpeta/carpeta_proyecto)
		//Se elimina lo anterior debido a que existe el metodo GetPath();
		Path rutaArchivo = getPath(nombreArchivo); 
		
		log.info(rutaArchivo.toString()); //se imprime el log
		
	//try {
			Files.copy(archivo.getInputStream(), rutaArchivo);
	//	} catch (IOException e) {  //Se elimina el try catch, ya que el metodo arroja la excepcion que deberá ser manejada de la clase que llama al metodo (el controller)
			// TODO Auto-generated catch block
	//En caso de falla de la subida se ocupa el catch, el mensaje es algo más detallado para listar en angular.
	//response.put("mensaje", "Error al subir imagen del cliente " + nombreArchivo );
	//response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));	
	//return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
	//	}
		return nombreArchivo;
	}

	@Override
	public boolean eliminar(String nombreFoto) {
		// TODO Auto-generated method stub
		
		if (nombreFoto != null && nombreFoto.length()>0) {
			Path rutaFotoAnterior = getPath(nombreFoto);
			File archivoFotoAnterior =  rutaFotoAnterior.toFile();
			if (archivoFotoAnterior.exists() == true && archivoFotoAnterior.canRead() == true) {
				archivoFotoAnterior.delete();
				return true; 
			}
		}
		return false;
	}

	@Override
	public Path getPath(String nombreFoto) {
		// TODO Auto-generated method stub
		return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
	}

}
