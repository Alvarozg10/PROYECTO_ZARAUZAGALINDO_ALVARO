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

/**
 * Controlador encargado de gestionar el inicio de sesión de los usuarios.
 * 
 * Valida las credenciales introducidas y redirige al usuario
 * a la vista correspondiente según su perfil dentro del sistema.
 */
@Controller
public class LoginController implements Initializable {

    /** Campo para introducir la contraseña */
    @FXML private PasswordField password;

    /** Campo para introducir el nombre de usuario */
    @FXML private TextField username;

    /** Etiqueta para mostrar mensajes de error */
    @FXML private Label lblLogin;

    /** Servicio encargado de la autenticación de usuarios */
    @Autowired
    private UserService userService;

    /** Gestor de navegación entre vistas */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la vista limpiando los mensajes de error.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblLogin.setText("");
    }

    /**
     * Gestiona el proceso de inicio de sesión.
     * 
     * Comprueba las credenciales introducidas y, si son correctas,
     * guarda el usuario logueado y redirige a la vista correspondiente
     * según su perfil.
     * 
     * @param event evento de acción del botón de login
     */
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

        // Guarda el usuario logueado
        stageManager.setLoggedUser(user);

        // Redirección según el perfil del usuario
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