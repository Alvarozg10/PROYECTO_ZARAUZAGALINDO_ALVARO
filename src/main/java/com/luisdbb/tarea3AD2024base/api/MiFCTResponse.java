package com.luisdbb.tarea3AD2024base.api;

public class MiFCTResponse {

    private String empresa;

    private String tutor;

    private String estado;

    private String fechaInicio;

    private String fechaFin;

    public MiFCTResponse(
            String empresa,
            String tutor,
            String estado,
            String fechaInicio,
            String fechaFin) {

        this.empresa = empresa;
        this.tutor = tutor;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getTutor() {
        return tutor;
    }

    public String getEstado() {
        return estado;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }
}