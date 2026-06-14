package com.luisdbb.tarea3AD2024base.api;

public class CalendarioResponse {

    private String fecha;

    private Boolean asistio;

    public CalendarioResponse(
            String fecha,
            Boolean asistio) {

        this.fecha = fecha;
        this.asistio = asistio;
    }

    public String getFecha() {
        return fecha;
    }

    public Boolean getAsistio() {
        return asistio;
    }
}