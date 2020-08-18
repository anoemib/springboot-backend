package com.noemi.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@Entity                    //anotatios de la persistencia, se crea la entidad. Esto permite al FW entender que es una clase de tipo Entity
@Table(name="clientes")    //anotations nombre de la tabla
public class Cliente implements Serializable {   //implementa la interfaz serializable, para darle un numero serial id.

    @Id  //se define como clave primaria
    @GeneratedValue(strategy=GenerationType.IDENTITY)   //Estrategia como de genera el id de la clase, va a depender del motor de la bd. En este caso Mysql.
    private long id;
    // @Column()  Permite definir nombre del campo, tamaño, si es nullable, etc. Si es único, etc. 
    
    @NotEmpty //se puede customizar el mensaje de error. con el atributo (message="Xxxx")
    @Size(min=4, max=12) 
    @Column(nullable = false)
    private String nombre; 
    
    @NotEmpty
	private String apellido; //no tiene anotations, ya que tiene el mismo nombre en la tabla y no hay cambios.
	
    @NotEmpty
    @Email
	@Column(nullable = false, unique=true) 
	private String email;
	
    @NotNull(message="No puede estar vacio")
	@Column(name="create_at")  //Se define el nombre del campo en la base de datos. create_at.
	@Temporal(TemporalType.DATE)  //anotation formato temporal DATE. REVISAR LAS OTRAS OPCIONES dependiendo de la base de datos se transforman los formatos.   
	private Date createAt;
   
    
    private String foto;
    /* SE COMENTA PARA HACER EL EJEMPLO DE SELECCIÓN VÍA INTERFAZ
	@PrePersist  //Se define un metodo PrePersist, que se ejecuta antes de hacer el guardado de la bd. Se define fecha de creación automáticamente. 
	public void prePersist() {
		createAt = new Date();
	}
	*/
     //Carga perezosa, se carga la región solo si se invoca el getRegion. No cuando se invoca se llama al cliente. En el json aparece como un objeto relacionado.
    /*  Tener en cuenta que el framework, debido a que la referencia es del tipo Lazy
     *  crea otros atributos para referenciar la tabla.  Se deben omitir estos atributos en el Json. ya que están demas.
     *  se agrega el anotation @JsonIgnoreProperties
     * 
     *  Estos atributos son propios del proxy que está relacionado al objeto región. Son propiedades de Hibernate.
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  //Se ignoran los campos de propiedades del JPA
    @JoinColumn(name="region_id") //Esto lo peude crear automáticamente, toma el nombre de la clase y el nombre del id de la tabla referenciada. 
    @NotNull(message="La región no puede ser vacia")
    private Region region;  //Se declara la relación entre tablas. 
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}


	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}





	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;  //creador del id del serializable
	
}
