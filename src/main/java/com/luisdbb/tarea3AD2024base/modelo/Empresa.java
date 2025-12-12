package com.luisdbb.tarea3AD2024base.modelo;

public class Empresa {
    private long id;
    private String nombre;
    private String direccion;
    private String email;

    public Empresa() {}

    public Empresa(long id, String nombre, String direccion, String email) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    
}
