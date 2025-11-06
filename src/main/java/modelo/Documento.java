package modelo;

import java.time.LocalDateTime;

public class Documento {
    private long idDocumento;
    private String nombreArchivo;
    private String tipo;
    private String ruta;
    private LocalDateTime fechaSubida;

    public Documento() {}

    public Documento(long idDocumento, String nombreArchivo, String tipo, String ruta, LocalDateTime fechaSubida) {
        this.idDocumento = idDocumento;
        this.nombreArchivo = nombreArchivo;
        this.tipo = tipo;
        this.ruta = ruta;
        this.fechaSubida = fechaSubida;
    }

	public long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public LocalDateTime getFechaSubida() {
		return fechaSubida;
	}

	public void setFechaSubida(LocalDateTime fechaSubida) {
		this.fechaSubida = fechaSubida;
	}

    
}
