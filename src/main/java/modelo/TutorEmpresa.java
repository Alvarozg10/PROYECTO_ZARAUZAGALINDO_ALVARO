package modelo;

public class TutorEmpresa {
    private long idTutorEmpresa;
    private String telefono;
    private String puesto;
    private Empresa empresa;

    public TutorEmpresa() {}

    public TutorEmpresa(long idTutorEmpresa, String telefono, String puesto, Empresa empresa) {
        this.idTutorEmpresa = idTutorEmpresa;
        this.telefono = telefono;
        this.puesto = puesto;
        this.empresa = empresa;
    }

	public long getIdTutorEmpresa() {
		return idTutorEmpresa;
	}

	public void setIdTutorEmpresa(long idTutorEmpresa) {
		this.idTutorEmpresa = idTutorEmpresa;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

    
}
