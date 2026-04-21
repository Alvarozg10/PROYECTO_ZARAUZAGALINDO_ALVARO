package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "formacion_empresa")
public class FormacionEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private User estudiante;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private User tutor;

    private String empresa;

    private Date fechaInicio;
    private Date fechaFin;

    private String estado;

    public Long getId() { return id; }

    public User getEstudiante() { return estudiante; }
    public void setEstudiante(User estudiante) { this.estudiante = estudiante; }

    public User getTutor() { return tutor; }
    public void setTutor(User tutor) { this.tutor = tutor; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
