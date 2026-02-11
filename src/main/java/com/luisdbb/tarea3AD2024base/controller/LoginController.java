package com.luisdbb.tarea3AD2024base.controller;


import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Controller
public class LoginController implements Initializable{

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
    
    @FXML
    private ChoiceBox<String> eleccionUsuario;
    
    @FXML
    private void login(ActionEvent event) throws IOException {

        User user = userService.authenticate(getUsername(), getPassword());

        if (user == null) {
            lblLogin.setText("Email o contraseña incorrectos.");
            return;
        }

        String rolSeleccionado = eleccionUsuario.getValue() != null
                ? eleccionUsuario.getValue().trim()
                : "";

        String rolUsuario = user.getRol() != null
                ? user.getRol().trim()
                : "";

        if (!rolUsuario.equals(rolSeleccionado)) {
            lblLogin.setText("Credenciales correctas pero rol incorrecto.");
            return;
        }

        switch (rolUsuario) {

            case "Administrador":
                stageManager.switchScene(FxmlView.ADMINISTRADOR1);
                break;

            case "Profesor":
                stageManager.switchScene(FxmlView.PROFESOR1);
                break;

            case "Estudiante":
                stageManager.switchScene(FxmlView.ESTUDIANTE1);
                break;

            case "Tutor de empresa":
                stageManager.switchScene(FxmlView.TUTOREMPRESA1);
                break;

            default:
                lblLogin.setText("Rol no reconocido: " + rolUsuario);
        }
    }

	
	public String getPassword() {
		return password.getText();
	}

	public String getUsername() {
		return username.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	    eleccionUsuario.getItems().addAll(
	            "Administrador",
	            "Profesor",
	            "Estudiante",
	            "Tutor de empresa"
	    );

	    eleccionUsuario.setValue("Estudiante");
	    username.clear();
	    password.clear();
	    lblLogin.setText("");
	}

}
