package modelo;

public class Profesor extends Usuario {
    private long idProfesor;
    private String telefono;
    private boolean esCoordinador;
    private String departamento;

    public Profesor() {}

    public Profesor(long idUsuario, String nombre, String email, String contraseña,
                    long idProfesor, String telefono, boolean esCoordinador, String departamento) {
        super(idUsuario, nombre, email, contraseña);
        this.idProfesor = idProfesor;
        this.telefono = telefono;
        this.esCoordinador = esCoordinador;
        this.departamento = departamento;
    }

	public long getIdProfesor() {
		return idProfesor;
	}

	public void setIdProfesor(long idProfesor) {
		this.idProfesor = idProfesor;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public boolean isEsCoordinador() {
		return esCoordinador;
	}

	public void setEsCoordinador(boolean esCoordinador) {
		this.esCoordinador = esCoordinador;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

    
}
