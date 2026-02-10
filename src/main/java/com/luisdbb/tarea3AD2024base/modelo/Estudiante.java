package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Estudiante")
public class Estudiante extends User {

    private String ciclo;

    private Integer curso;   

    private String expediente;

    public Estudiante() {
        super();
        setRol("Estudiante");
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Integer getCurso() {     
        return curso;
    }

    public void setCurso(Integer curso) {  
        this.curso = curso;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }
}
