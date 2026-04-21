package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

@Controller
public class LoginController implements Initializable {

    @FXML private PasswordField password;
    @FXML private TextField username;
    @FXML private Label lblLogin;

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblLogin.setText("");
    }

    @FXML
    private void login(ActionEvent event) {

        User user = userService.authenticate(
                username.getText(),
                password.getText()
        );

        if (user == null) {
            lblLogin.setText("Credenciales incorrectas");
            return;
        }

        stageManager.setLoggedUser(user);

        switch (user.getPerfil()) {

            case "ADMIN":
                stageManager.switchScene(FxmlView.ADMIN);
                break;

            case "PROFESOR":
                stageManager.switchScene(FxmlView.PROFESOR);
                break;

            case "ESTUDIANTE":
                stageManager.switchScene(FxmlView.ESTUDIANTE);
                break;

            case "TUTOR_EMPRESA":
                stageManager.switchScene(FxmlView.TUTOR_EMPRESA);
                break;

            default:
                lblLogin.setText("Perfil desconocido");
        }
    }
}