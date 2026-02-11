package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

@Controller
public class TutorEmpresaController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;

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
}
