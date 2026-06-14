package com.luisdbb.tarea3AD2024base.api;

public class AusenciaResponse {

    private String fecha;

    private String motivo;

    private String comentario;

    public AusenciaResponse(
            String fecha,
            String motivo,
            String comentario) {

        this.fecha = fecha;
        this.motivo = motivo;
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getComentario() {
        return comentario;
    }
}