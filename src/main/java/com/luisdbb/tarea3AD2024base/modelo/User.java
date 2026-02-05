package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long idUsuario;

    private String nombre;

    private String apellidos;

    private String genero;

    @Column(unique = true)
    private String email;

    private String contraseña;

    private Date fechaNacimiento;

    @Column(name = "rol")
    private String rol;

    
    public User() {
    }

    // Constructor completo
    public User(long idUsuario, String nombre, String apellidos, String genero,
                String email, String contraseña, Date fechaNacimiento, String rol) {

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.email = email;
        this.contraseña = contraseña;
        this.fechaNacimiento = fechaNacimiento;
        this.rol = rol;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    // 🔥 GETTER / SETTER DEL ROL
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "User [idUsuario=" + idUsuario +
               ", nombre=" + nombre +
               ", apellidos=" + apellidos +
               ", genero=" + genero +
               ", email=" + email +
               ", rol=" + rol +
               ", fechaNacimiento=" + fechaNacimiento + "]";
    }
}
