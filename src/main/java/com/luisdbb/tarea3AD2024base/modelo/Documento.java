package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;

public class Documento {
	private Long idDocumento;
	private String nombre;
	private TipoDocumento tipo;
	private String ruta;
	private Date fechaSubida;
	private Estado estado;


	public Documento(Long idDocumento, String nombre, TipoDocumento tipo,
			String ruta, Date fechaSubida, Estado estado) {
		this.idDocumento = idDocumento;
		this.nombre = nombre;
		this.tipo = tipo;
		this.ruta = ruta;
		this.fechaSubida = fechaSubida;
		this.estado = estado;
		}

	public Long getIdDocumento() {
		return idDocumento;
	}


	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public TipoDocumento getTipo() {
		return tipo;
	}


	public void setTipo(TipoDocumento tipo) {
		this.tipo = tipo;
	}


	public String getRuta() {
		return ruta;
	}


	public void setRuta(String ruta) {
		this.ruta = ruta;
	}


	public Date getFechaSubida() {
		return fechaSubida;
	}


	public void setFechaSubida(Date fechaSubida) {
		this.fechaSubida = fechaSubida;
	}


	public Estado getEstado() {
		return estado;
	}


	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Documento [idDocumento=" + idDocumento + ", nombre=" + nombre + ", tipo=" + tipo + ", ruta=" + ruta
				+ ", fechaSubida=" + fechaSubida + ", estado=" + estado + "]";
	}
	
	}