package com.luisdbb.tarea3AD2024base.modelo;

public class Empresa {
	private Long idEmpresa;
	private String nombre;
	private String cif;
	private String direccion;
	private String telefono;
	private String email;
	
	public Empresa(Long idEmpresa, String nombre, String cif, String direccion, String telefono, String email) {
		super();
		this.idEmpresa = idEmpresa;
		this.nombre = nombre;
		this.cif = cif;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
	}
	
	public Long getIdEmpresa() {
		return idEmpresa;
	}



	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getCif() {
		return cif;
	}



	public void setCif(String cif) {
		this.cif = cif;
	}



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Empresa [idEmpresa=" + idEmpresa + ", nombre=" + nombre + ", cif=" + cif + ", direccion=" + direccion
				+ ", telefono=" + telefono + ", email=" + email + "]";
	}
	
}
