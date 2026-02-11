package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("Estudiante")
public class Estudiante extends User {

    private String ciclo;

    private Integer curso;   

    private String expediente;
    
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "tutor_empresa_id")
    private TutorEmpresa tutorEmpresa;

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
    
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public TutorEmpresa getTutorEmpresa() {
        return tutorEmpresa;
    }

    public void setTutorEmpresa(TutorEmpresa tutorEmpresa) {
        this.tutorEmpresa = tutorEmpresa;
    }

}
