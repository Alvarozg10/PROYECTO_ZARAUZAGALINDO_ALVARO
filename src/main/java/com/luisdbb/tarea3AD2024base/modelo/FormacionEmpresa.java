package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa una Formación en Centro de Trabajo (FE).
 * 
 * Relaciona a un estudiante con un tutor de empresa, incluyendo la empresa,
 * fechas de inicio y fin, y el estado actual de la formación.
 */
@Entity
@Table(name = "formacion_empresa")
public class FormacionEmpresa {

    /** Identificador único de la FE */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Estudiante asignado a la FE.
     * Relación muchos a uno con la entidad User.
     */
    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private User estudiante;

    /**
     * Tutor de empresa responsable de la FE.
     * Relación muchos a uno con la entidad User.
     */
    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private User tutor;

    /** Empresa donde se realiza la FE */
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    /** Fecha de inicio de la FE */
    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;

    /** Fecha de finalización de la FE */
    @Column(name = "fecha_fin", nullable = false)
    private Date fechaFin;

    /** Estado actual de la FE (PENDIENTE, EN_CURSO, FINALIZADO) */
    @Enumerated(EnumType.STRING)
    private Estado estado;
    
    @OneToOne(mappedBy = "fe",
            cascade = CascadeType.ALL)
    private Evaluacion evaluacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(User estudiante) {
		this.estudiante = estudiante;
	}

	public User getTutor() {
		return tutor;
	}

	public void setTutor(User tutor) {
		this.tutor = tutor;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public Evaluacion getEvaluacion() {
	    return evaluacion;
	}

	public void setEvaluacion(Evaluacion evaluacion) {
	    this.evaluacion = evaluacion;
	}

}