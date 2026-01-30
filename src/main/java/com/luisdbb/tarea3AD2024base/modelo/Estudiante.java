package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;

public class Estudiante extends User{
	
	private String ciclo;
	
	private int curso;
	
	private String expediente;

	public Estudiante(long idUsuario, String nombre, String apellidos, String genero, String email, String contraseña,
			Date fechaNacimiento, String ciclo, int curso, String expediente) {
		super(idUsuario, nombre, apellidos, genero, email, contraseña, fechaNacimiento);
		this.ciclo = ciclo;
		this.curso = curso;
		this.expediente = expediente;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		this.curso = curso;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	@Override
	public String toString() {
		return "Estudiante [ciclo=" + ciclo + ", curso=" + curso + ", expediente=" + expediente + "]";
	}
	
}
