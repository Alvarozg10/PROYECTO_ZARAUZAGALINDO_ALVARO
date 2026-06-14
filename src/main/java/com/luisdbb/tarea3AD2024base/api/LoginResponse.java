package com.luisdbb.tarea3AD2024base.api;

public class LoginResponse {

    private Long id;

    private String nombre;

    private String apellidos;

    private String perfil;

    private String email;

    public LoginResponse() {
    }

    public LoginResponse(
            Long id,
            String nombre,
            String apellidos,
            String perfil,
            String email) {

        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.perfil = perfil;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getPerfil() {
        return perfil;
    }

    public String getEmail() {
        return email;
    }
}