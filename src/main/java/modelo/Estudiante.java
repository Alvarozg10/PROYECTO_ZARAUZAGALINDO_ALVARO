package modelo;

public class Estudiante extends Usuario {
    private long idEstudiante;
    private int nia;
    private String telefono;
    private int curso;
    private String ciclo;

    public Estudiante() {}

    public Estudiante(long idUsuario, String nombre, String email, String contraseña,
                      long idEstudiante, int nia, String telefono, int curso, String ciclo) {
        super(idUsuario, nombre, email, contraseña);
        this.idEstudiante = idEstudiante;
        this.nia = nia;
        this.telefono = telefono;
        this.curso = curso;
        this.ciclo = ciclo;
    }

	public long getIdEstudiante() {
		return idEstudiante;
	}

	public void setIdEstudiante(long idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	public int getNia() {
		return nia;
	}

	public void setNia(int nia) {
		this.nia = nia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		this.curso = curso;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

    
}
