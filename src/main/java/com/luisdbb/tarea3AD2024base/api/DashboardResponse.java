package com.luisdbb.tarea3AD2024base.api;

public class DashboardResponse {

    private int horasCotizadas;

    private int faltas;

    private int diasAsistidos;

    private int diasTotales;

    private long diasRestantes;

    private int porcentajeCompletado;

    public DashboardResponse(
            int horasCotizadas,
            int faltas,
            int diasAsistidos,
            int diasTotales,
            long diasRestantes,
            int porcentajeCompletado) {

        this.horasCotizadas = horasCotizadas;
        this.faltas = faltas;
        this.diasAsistidos = diasAsistidos;
        this.diasTotales = diasTotales;
        this.diasRestantes = diasRestantes;
        this.porcentajeCompletado = porcentajeCompletado;
    }

    public int getHorasCotizadas() {
        return horasCotizadas;
    }

    public int getFaltas() {
        return faltas;
    }

    public int getDiasAsistidos() {
        return diasAsistidos;
    }

    public int getDiasTotales() {
        return diasTotales;
    }

    public long getDiasRestantes() {
        return diasRestantes;
    }

    public int getPorcentajeCompletado() {
        return porcentajeCompletado;
    }
}