package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;

import jakarta.persistence.Entity;

@Entity
public class Estudiante extends User{
	
	private String ciclo;
	
	private int curso;
	
	private String expediente;

	public Estudiante() {
	super();
	setRol("Estudiante");
	
	}

		public Estudiante(long idUsuario,
						String nombre,
						String apellidos,
						String genero,
						String email,
						String contrasena,
						Date fechaNacimiento,
						String ciclo,
						int curso,
						String expediente) {

        	
		super(idUsuario, nombre, apellidos, genero,
		              email, contrasena, fechaNacimiento,
		              "Estudiante");

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
		
}


