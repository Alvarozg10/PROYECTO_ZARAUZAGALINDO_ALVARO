package com.luisdbb.tarea3AD2024base.modelo;

public class SeguimientoAlumnoDTO {

    private String nombre;
    private long horas;
    private long asistencias;
    private long faltas;

    public SeguimientoAlumnoDTO(
            String nombre,
            long horas,
            long asistencias,
            long faltas) {

        this.nombre = nombre;
        this.horas = horas;
        this.asistencias = asistencias;
        this.faltas = faltas;
    }

    public String getNombre() {
        return nombre;
    }

    public long getHoras() {
        return horas;
    }

    public long getAsistencias() {
        return asistencias;
    }

    public long getFaltas() {
        return faltas;
    }
}