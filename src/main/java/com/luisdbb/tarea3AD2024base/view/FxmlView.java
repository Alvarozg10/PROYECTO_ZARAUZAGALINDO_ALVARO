package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

public enum FxmlView {

    LOGIN("Login", "login.fxml"),
    ESTUDIANTE("Panel Estudiante", "Estudiante1.fxml"),
    PROFESOR("Panel Profesor", "Profesor1.fxml"),
    TUTOR_EMPRESA("Panel Tutor Empresa", "TutorEmpresa1.fxml"),
    GESTIONAR_FCT("Panel FCT", "GestionarFCT.fxml"),
	ADMIN("Panel Administrador", "Administrador1.fxml");

    private final String title;
    private final String fxmlFile;

    FxmlView(String title, String fxmlFile) {
        this.title = title;
        this.fxmlFile = fxmlFile;
    }

    public String getTitle() {
        return title;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }
}


