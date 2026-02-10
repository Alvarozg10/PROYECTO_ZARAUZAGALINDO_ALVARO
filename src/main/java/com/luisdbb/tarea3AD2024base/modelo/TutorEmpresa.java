package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TutorEmpresa")
public class TutorEmpresa extends User {

    private String telefono;

    public TutorEmpresa() {
        super();
        setRol("Tutor de empresa");
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
