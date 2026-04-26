package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;
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

    @Autowired
    private UserService userService;

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
    private void verEmpresaYTutor(ActionEvent event) {
        stageManager.switchScene(FxmlView.DATOS_FCT_ALUMNO);
    }
    
    @FXML
    private void verDocumentos() {
        stageManager.switchScene(FxmlView.DOCUMENTOS_ALUMNO);
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        stageManager.switchScene(FxmlView.LOGIN);
    }

}