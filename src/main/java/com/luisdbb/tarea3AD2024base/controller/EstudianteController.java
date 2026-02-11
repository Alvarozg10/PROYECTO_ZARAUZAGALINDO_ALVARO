package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

@Controller
public class EstudianteController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private Label lblEstudiante;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = stageManager.getLoggedUser();
        if (user != null) {
        	lblEstudiante.setText("Hola, " + user.getNombre());
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        stageManager.switchScene(FxmlView.LOGIN);
    }
    
    @FXML
    private void verEmpresaYTutor(ActionEvent event) {

        User user = stageManager.getLoggedUser();

        if (user instanceof Estudiante estudiante) {

            String empresa = estudiante.getEmpresa() != null
                    ? estudiante.getEmpresa().getNombre()
                    : "No asignada";

            String tutor = estudiante.getTutorEmpresa() != null
                    ? estudiante.getTutorEmpresa().getNombre()
                    : "No asignado";

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información FCT");
            alert.setHeaderText("Datos asignados");
            alert.setContentText(
                    "Empresa: " + empresa + "\n" +
                    "Tutor de empresa: " + tutor
            );

            alert.showAndWait();
        }
    }

}
