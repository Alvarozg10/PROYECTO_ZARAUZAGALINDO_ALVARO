package com.luisdbb.tarea3AD2024base.modelo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FormacionEmpresa<TutorEmpresa> {
	
	private Long idFormacion;
	private Date fechaAsignacion;
	private Estudiante estudiante;
	private Profesor profesor;
	private TutorEmpresa tutorEmpresa;
	private Empresa empresa;
	private Periodo periodo;
	private List<Documento> documentos = new ArrayList<>();

	public FormacionEmpresa(Long idFormacion, Date fechaAsignacion,
			Estudiante estudiante, Profesor profesor,
			TutorEmpresa tutorEmpresa, Empresa empresa,
			Periodo periodo) {
		this.idFormacion = idFormacion;
		this.fechaAsignacion = fechaAsignacion;
		this.estudiante = estudiante;
		this.profesor = profesor;
		this.tutorEmpresa = tutorEmpresa;
		this.empresa = empresa;
		this.periodo = periodo;
	}

	public void addDocumento(Documento documento) {
	documentos.add(documento);
	
	}

	public Long getIdFormacion() {
		return idFormacion;
	}

	public void setIdFormacion(Long idFormacion) {
		this.idFormacion = idFormacion;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public TutorEmpresa getTutorEmpresa() {
		return tutorEmpresa;
	}

	public void setTutorEmpresa(TutorEmpresa tutorEmpresa) {
		this.tutorEmpresa = tutorEmpresa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	@Override
	public String toString() {
		return "FormacionEmpresa [idFormacion=" + idFormacion + ", fechaAsignacion=" + fechaAsignacion + ", estudiante="
				+ estudiante + ", profesor=" + profesor + ", tutorEmpresa=" + tutorEmpresa + ", empresa=" + empresa
				+ ", periodo=" + periodo + ", documentos=" + documentos + "]";
	}
		
	}