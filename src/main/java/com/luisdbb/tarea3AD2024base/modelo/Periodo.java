package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;

/**
 * Clase que representa un periodo temporal dentro del sistema.
 * 
 * Un periodo tiene un nombre, un tipo (ordinario o extraordinario)
 * y un rango de fechas definido por una fecha de inicio y una de fin.
 */
public class Periodo {

    /** Identificador único del periodo */
    private Long idPeriodo;

    /** Nombre del periodo */
    private String nombre;

    /** Tipo de periodo (ORDINARIO o EXTRAORDINARIO) */
    private TipoPeriodo tipo;

    /** Fecha de inicio del periodo */
    private Date fechaInicio;

    /** Fecha de finalización del periodo */
    private Date fechaFin;

    /**
     * Constructor de la clase Periodo.
     * 
     * @param idPeriodo identificador del periodo
     * @param nombre nombre del periodo
     * @param tipo tipo de periodo
     * @param fechaInicio fecha de inicio
     * @param fechaFin fecha de fin
     */
    public Periodo(Long idPeriodo, String nombre, TipoPeriodo tipo,
                   Date fechaInicio, Date fechaFin) {
        this.idPeriodo = idPeriodo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoPeriodo getTipo() {
        return tipo;
    }

    public void setTipo(TipoPeriodo tipo) {
        this.tipo = tipo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Devuelve una representación en texto del periodo.
     * 
     * @return cadena con los datos del periodo
     */
    @Override
    public String toString() {
        return "Periodo [idPeriodo=" + idPeriodo + ", nombre=" + nombre + ", tipo=" + tipo +
                ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + "]";
    }
}