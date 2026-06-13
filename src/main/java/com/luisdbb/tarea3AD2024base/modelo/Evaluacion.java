package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fe_id")
    private FormacionEmpresa fe;

    private Integer puntualidad;

    private Integer responsabilidad;

    private Integer trabajoEquipo;

    private Integer iniciativa;

    @Column(length = 1000)
    private String observaciones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormacionEmpresa getFe() {
        return fe;
    }

    public void setFe(FormacionEmpresa fe) {
        this.fe = fe;
    }

    public Integer getPuntualidad() {
        return puntualidad;
    }

    public void setPuntualidad(Integer puntualidad) {
        this.puntualidad = puntualidad;
    }

    public Integer getResponsabilidad() {
        return responsabilidad;
    }

    public void setResponsabilidad(Integer responsabilidad) {
        this.responsabilidad = responsabilidad;
    }

    public Integer getTrabajoEquipo() {
        return trabajoEquipo;
    }

    public void setTrabajoEquipo(Integer trabajoEquipo) {
        this.trabajoEquipo = trabajoEquipo;
    }

    public Integer getIniciativa() {
        return iniciativa;
    }

    public void setIniciativa(Integer iniciativa) {
        this.iniciativa = iniciativa;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public double getMedia() {

        return (
                puntualidad
                + responsabilidad
                + trabajoEquipo
                + iniciativa
        ) / 4.0;
    }
}