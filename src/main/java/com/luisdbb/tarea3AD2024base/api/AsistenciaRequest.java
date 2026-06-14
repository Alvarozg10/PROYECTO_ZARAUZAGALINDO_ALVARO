package com.luisdbb.tarea3AD2024base.api;

public class AsistenciaRequest {

    private Long idAlumno;

    private Boolean asistio;

    private String motivo;

    private String comentario;

    private String justificante;

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Boolean getAsistio() {
        return asistio;
    }

    public void setAsistio(Boolean asistio) {
        this.asistio = asistio;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getJustificante() {
        return justificante;
    }

    public void setJustificante(String justificante) {
        this.justificante = justificante;
    }
}