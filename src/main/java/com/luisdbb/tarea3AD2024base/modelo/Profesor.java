package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;

class Profesor extends User {
	
		private String especialidad;

		public Profesor(Long idUsuario, String nombre, String apellidos, String genero,
		String email, String contrasena, Date fechaNacimiento,
		String especialidad) {
		super(idUsuario, nombre, apellidos, genero, email, contrasena, fechaNacimiento);
		this.especialidad = especialidad;
		
		}

		@Override
		public String toString() {
			return "Profesor [especialidad=" + especialidad + "]";
		}
		
	}


