package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;

public class Periodo {
	private Long idPeriodo;
	private String nombre;
	private TipoPeriodo tipo;
	private Date fechaInicio;
	private Date fechaFin;


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

	@Override
	public String toString() {
		return "Periodo [idPeriodo=" + idPeriodo + ", nombre=" + nombre + ", tipo=" + tipo + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + "]";
	}
		
	}