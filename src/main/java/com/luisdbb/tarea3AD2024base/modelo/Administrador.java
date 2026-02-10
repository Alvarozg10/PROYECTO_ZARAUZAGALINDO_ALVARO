package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Administrador")
public class Administrador extends User {

    public Administrador() {
        super();
        setRol("Administrador");
    }
}