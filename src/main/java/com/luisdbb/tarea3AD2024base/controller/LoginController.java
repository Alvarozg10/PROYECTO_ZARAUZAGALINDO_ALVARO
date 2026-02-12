package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Administrador;
import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.Profesor;
import com.luisdbb.tarea3AD2024base.modelo.TutorEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label lblLogin;

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

        String email = username.getText();
        String pass = password.getText();

        if (email.isEmpty() || pass.isEmpty()) {
            lblLogin.setStyle("-fx-text-fill: red;");
            lblLogin.setText("Introduzca usuario y contraseña.");
            return;
        }

        User user = userService.authenticate(email, pass);

        if (user == null) {
            lblLogin.setStyle("-fx-text-fill: red;");
            lblLogin.setText("Credenciales incorrectas. Verifique los datos.");
            return;
        }

        // Guardamos usuario en sesión
        stageManager.setLoggedUser(user);

        // Redirección automática por tipo
        if (user instanceof Estudiante) {
            stageManager.switchScene(FxmlView.ESTUDIANTE);
        } 
        else if (user instanceof Profesor) {
            stageManager.switchScene(FxmlView.PROFESOR);
        } 
        else if (user instanceof TutorEmpresa) {
            stageManager.switchScene(FxmlView.TUTOR_EMPRESA);
        } 
        else if (user instanceof Administrador) {
            stageManager.switchScene(FxmlView.ADMIN);
        }
    }
}
