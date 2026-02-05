package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;

import jakarta.persistence.Entity;

@Entity
public class TutorEmpresa extends User {

    private String telefono;

    public TutorEmpresa() {
        super();
        setRol("Tutor de empresa");
    }

    public TutorEmpresa(long idUsuario,
                        String nombre,
                        String apellidos,
                        String genero,
                        String email,
                        String contrasena,
                        Date fechaNacimiento,
                        String telefono) {

        super(idUsuario, nombre, apellidos, genero,
              email, contrasena, fechaNacimiento,
              "Tutor de empresa");

        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
