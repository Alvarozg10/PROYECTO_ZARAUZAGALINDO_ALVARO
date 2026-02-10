package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Profesor")
public class Profesor extends User {

    private String especialidad;

    public Profesor() {
        super();
        setRol("Profesor");
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}

