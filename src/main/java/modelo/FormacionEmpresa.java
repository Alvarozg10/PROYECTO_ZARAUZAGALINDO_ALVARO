package modelo;

import java.util.Date;

public class FormacionEmpresa {
    private long idFormacion;
    private Date fechaInicio;
    private Date fechaFin;
    private Periodo periodo;
    private Estado estado;
    private String cursoAcademico;

    public FormacionEmpresa() {}

    public FormacionEmpresa(long idFormacion, Date fechaInicio, Date fechaFin,
                            Periodo periodo, Estado estado, String cursoAcademico) {
        this.idFormacion = idFormacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.periodo = periodo;
        this.estado = estado;
        this.cursoAcademico = cursoAcademico;
    }

	public long getIdFormacion() {
		return idFormacion;
	}

	public void setIdFormacion(long idFormacion) {
		this.idFormacion = idFormacion;
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

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getCursoAcademico() {
		return cursoAcademico;
	}

	public void setCursoAcademico(String cursoAcademico) {
		this.cursoAcademico = cursoAcademico;
	}

    
}
