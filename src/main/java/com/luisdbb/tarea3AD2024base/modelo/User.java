package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;
import jakarta.persistence.*;

/**
 * Entidad que representa un usuario del sistema.
 * 
 * Un usuario puede tener distintos roles (administrador, profesor,
 * estudiante o tutor de empresa) y contiene información personal
 * y de acceso a la aplicación.
 */
@Entity
@Table(name = "user")
public class User {

    /** Identificador único del usuario */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idUsuario;

    /** Nombre del usuario */
    private String nombre;

    /** Apellidos del usuario */
    private String apellidos;

    /** Correo electrónico del usuario */
    private String email;

    /** Contraseña del usuario */
    private String password;

    /** Género del usuario */
    private String genero;

    /** Fecha de nacimiento del usuario */
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    /**
     * Perfil del usuario dentro del sistema
     * (ADMIN, PROFESOR, ESTUDIANTE, TUTOR_EMPRESA)
     */
    private String perfil;

    /** Teléfono de contacto del usuario */
    @Column(name = "telefono")
    private String telefono;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
 
}