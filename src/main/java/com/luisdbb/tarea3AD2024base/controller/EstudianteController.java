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

/**
 * Controlador encargado de gestionar el panel del estudiante.
 * 
 * Permite al alumno acceder a sus datos de FCT, consultar documentos
 * y cerrar la sesión.
 */
@Controller
public class EstudianteController implements Initializable {

    /** Gestor de navegación entre vistas */
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    @Autowired
    private com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader springFXMLLoader;


    /** Servicio de usuarios (no utilizado directamente en este controlador) */
    @Autowired
    private UserService userService;

    /** Etiqueta que muestra el saludo al estudiante */
    @FXML
    private Label lblEstudiante;

    /**
     * Inicializa la vista mostrando el nombre del usuario logueado.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = stageManager.getLoggedUser();
        if (user != null) {
            lblEstudiante.setText("Hola, " + user.getNombre());
        }
    }
   
    /**
     * Redirige a la vista donde el alumno puede consultar
     * su empresa y tutor asignados.
     */
    @FXML
    private void verEmpresaYTutor(ActionEvent event) {
        stageManager.switchScene(FxmlView.DATOS_FCT_ALUMNO);
    }
    
    /**
     * Redirige a la vista de documentos del alumno.
     */
    @FXML
    private void verDocumentos() {
        stageManager.switchScene(FxmlView.DOCUMENTOS_ALUMNO);
    }
    
    /**
     * Abre el sistema de ayuda en una ventana independiente.
     * 
     * La ayuda se carga desde un archivo FXML que contiene un WebView,
     * el cual muestra contenido HTML almacenado en los recursos
     * de la aplicación.
     */
    @FXML
    private void handleAyuda() {
        try {
            javafx.scene.Parent root = springFXMLLoader.load("Ayuda.fxml");

            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setTitle("Sistema de Ayuda");
            stage.setScene(new javafx.scene.Scene(root, 800, 600));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cierra la sesión del usuario y vuelve a la pantalla de login.
     * 
     * @param event evento de acción del botón
     */
    @FXML
    private void cerrarSesion(ActionEvent event) {
        stageManager.switchScene(FxmlView.LOGIN);
    }
}