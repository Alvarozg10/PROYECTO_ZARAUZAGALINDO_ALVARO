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

/**
 * Controlador encargado de gestionar el panel del profesor.
 * 
 * Permite al profesor acceder a las funcionalidades principales del sistema,
 * como crear FE, consultar las existentes, gestionar estudiantes y cerrar sesión.
 */
@Controller
public class ProfesorController implements Initializable {

    /** Gestor de navegación entre vistas */
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    @Autowired
    private com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader springFXMLLoader;


    /** Etiqueta que muestra el saludo al profesor */
    @FXML
    private Label lblProfesor;

    /**
     * Inicializa la vista mostrando el nombre del profesor logueado.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = stageManager.getLoggedUser();
        if (user != null) {
            lblProfesor.setText("Hola, " + user.getNombre());
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
    
    /**
     * Redirige a la vista de creación de FCT.
     */
    @FXML
    private void crearFCT() {
        stageManager.switchScene(FxmlView.CREARFCT);
    }
    
    /**
     * Abre la vista de consulta de FCT.
     */
    @FXML
    private void abrirGestionFCT() {
        stageManager.switchScene(FxmlView.CONSULTARFCT);
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
     * Redirige a la gestión de estudiantes.
     */
    @FXML
    private void gestionarEstudiantes() {
        stageManager.switchScene(FxmlView.GESTIONAR_ESTUDIANTES);
    }
}