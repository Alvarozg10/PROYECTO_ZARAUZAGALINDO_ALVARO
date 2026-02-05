package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;

import jakarta.persistence.Entity;

@Entity
public class Profesor extends User {
	
		private String especialidad;

		public Profesor() {
		super();
		setRol("Profesor");
		
		}

		public Profesor(long idUsuario,
						String nombre,
						String apellidos,
						String genero,
						String email,
						String contrasena,
						Date fechaNacimiento,
						String especialidad) {
        	
		super(idUsuario, nombre, apellidos, genero,
		              email, contrasena, fechaNacimiento,
		              "Profesor");

		        this.especialidad = especialidad;
		    }

		public String getEspecialidad() {
			return especialidad;
		}

		public void setEspecialidad(String especialidad) {
			this.especialidad = especialidad;
		}

}


