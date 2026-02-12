package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;


@Controller
public class TutorEmpresaController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private UserService userService;

    @FXML
    private Label lblTutorEmpresa;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = stageManager.getLoggedUser();
        if (user != null) {
            lblTutorEmpresa.setText("Hola, " + user.getNombre());
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @FXML
    private void verAlumnoAsignado(ActionEvent event) {

        User tutor = stageManager.getLoggedUser();

        if (tutor == null) {
            mostrarAlerta("Error", "No se pudo obtener el tutor logueado.");
            return;
        }

        List<Estudiante> alumnos =
                userService.findEstudiantesByTutor(tutor.getIdUsuario());

        if (alumnos.isEmpty()) {
            mostrarAlerta("Información", "No tiene alumnos asignados.");
        } else {

            StringBuilder mensaje = new StringBuilder();

            for (Estudiante alumno : alumnos) {
                mensaje.append(alumno.getNombre())
                       .append(" ")
                       .append(alumno.getApellidos())
                       .append("\n");
            }

            mostrarAlerta("Alumno(s) asignado(s)", mensaje.toString());
        }
    }
    
    private void mostrarAlerta(String titulo, String contenido) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

}

