package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;
import jakarta.persistence.*;

/**
 * Entidad que representa un documento dentro del sistema.
 * Asociado a un estudiante y almacenado mediante una ruta.
 */
@Entity
public class Documento {

    /** Identificador único del documento */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del documento */
    private String nombre;

    /** Ruta donde se almacena el archivo */
    private String ruta;

    /**
     * Estudiante al que pertenece el documento.
     * Relación muchos a uno.
     */
    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private User estudiante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public User getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(User estudiante) {
        this.estudiante = estudiante;
    }
}