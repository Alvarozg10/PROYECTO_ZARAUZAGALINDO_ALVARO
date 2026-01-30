package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;

class TutorEmpresa extends User {
	private String telefono;


	public TutorEmpresa(Long idUsuario, String nombre, String apellidos, String genero,
			String email, String contrasena, Date fechaNacimiento,
			String telefono) {
			super(idUsuario, nombre, apellidos, genero, email, contrasena, fechaNacimiento);
			this.telefono = telefono;
	}
}
