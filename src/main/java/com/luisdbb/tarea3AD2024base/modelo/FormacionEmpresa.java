package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.*;
import java.sql.Date;

/**
 * Entidad que representa una Formación en Centro de Trabajo (FCT).
 * 
 * Relaciona a un estudiante con un tutor de empresa, incluyendo la empresa,
 * fechas de inicio y fin, y el estado actual de la formación.
 */
@Entity
@Table(name = "formacion_empresa")
public class FormacionEmpresa {

    /** Identificador único de la FCT */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Estudiante asignado a la FCT.
     * Relación muchos a uno con la entidad User.
     */
    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private User estudiante;

    /**
     * Tutor de empresa responsable de la FCT.
     * Relación muchos a uno con la entidad User.
     */
    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private User tutor;

    /** Nombre de la empresa donde se realiza la FCT */
    @Column(nullable = false)
    private String empresa;

    /** Fecha de inicio de la FCT */
    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;

    /** Fecha de finalización de la FCT */
    @Column(name = "fecha_fin", nullable = false)
    private Date fechaFin;

    /** Estado actual de la FCT (PENDIENTE, EN_CURSO, FINALIZADO) */
    @Enumerated(EnumType.STRING)
    private Estado estado;

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

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
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

}