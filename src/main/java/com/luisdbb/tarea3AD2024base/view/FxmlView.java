package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

public enum FxmlView {

    LOGIN {
        @Override
        public String getTitle() { return "Login"; }

        @Override
        public String getFxmlFile() { return "/fxml/Login.fxml"; }
    },

    PROFESOR {
        @Override
        public String getTitle() { return "Profesor"; }

        @Override
        public String getFxmlFile() { return "/fxml/Profesor.fxml"; }
    },

    ESTUDIANTE {
        @Override
        public String getTitle() { return "Estudiante"; }

        @Override
        public String getFxmlFile() { return "/fxml/Estudiante.fxml"; }
    },

    TUTOR {
        @Override
        public String getTitle() { return "Tutor de empresa"; }

        @Override
        public String getFxmlFile() { return "/fxml/Tutor.fxml"; }
    };

    public abstract String getTitle();
    public abstract String getFxmlFile();
}

