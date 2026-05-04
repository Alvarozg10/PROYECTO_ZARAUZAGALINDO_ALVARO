package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

/**
 * Enumeración que define las vistas de la aplicación.
 * 
 * Cada valor representa una pantalla del sistema, incluyendo
 * su título y el archivo FXML asociado.
 * 
 * Se utiliza junto con el StageManager para gestionar la
 * navegación entre las diferentes vistas de la aplicación.
 */
public enum FxmlView {

    LOGIN("Login", "login.fxml"),
    ESTUDIANTE("Panel Estudiante", "Estudiante1.fxml"),
    PROFESOR("Panel Profesor", "Profesor1.fxml"),
    TUTOR_EMPRESA("Panel Tutor Empresa", "TutorEmpresa1.fxml"),
    GESTIONAR_FCT("Panel FCT", "GestionarFCT.fxml"),
    ADMIN("Panel Administrador", "Administrador1.fxml"),
    ANADIRUSUARIO("Añadir Usuario", "AnadirUsuario.fxml"),
    MODIFICARUSUARIO("Modificar Usuario", "ModificarUsuario.fxml"),
    ELIMINARUSUARIO("Eliminar Usuario", "EliminarUsuario.fxml"),
    CREARFCT("Crear FCT", "CrearFCT.fxml"),
    CONSULTARFCT("Consultar FCT", "ConsultarFCT.fxml"),
    GESTIONAR_ESTUDIANTES("Gestionar Estudiantes", "GestionarEstudiantes.fxml"),
    ALUMNOS_TUTOR("Alumnos Tutor", "AlumnosTutor.fxml"),
    DATOS_FCT_ALUMNO("Mi FCT", "DatosFCTAlumno.fxml"),
    SUBIR_DOCUMENTO("Subir Documento", "SubirDocumento.fxml"),
    DOCUMENTOS_ALUMNO("Documentos", "DocumentosAlumno.fxml"),
    AYUDA("Ayuda", "Ayuda.fxml");

    /** Título de la ventana */
    private final String title;

    /** Nombre del archivo FXML asociado */
    private final String fxmlFile;

    /**
     * Constructor de la enumeración.
     * 
     * @param title título de la vista
     * @param fxmlFile archivo FXML asociado
     */
    FxmlView(String title, String fxmlFile) {
        this.title = title;
        this.fxmlFile = fxmlFile;
    }

    /**
     * Obtiene el título de la vista.
     * 
     * @return título de la ventana
     */
    public String getTitle() {
        return title;
    }

    /**
     * Obtiene el nombre del archivo FXML.
     * 
     * @return archivo FXML asociado
     */
    public String getFxmlFile() {
        return fxmlFile;
    }
}