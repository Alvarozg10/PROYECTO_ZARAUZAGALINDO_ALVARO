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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * Controlador encargado de gestionar el panel de administrador.
 * 
 * Permite al usuario administrador navegar entre las distintas
 * funcionalidades del sistema, como añadir, editar o eliminar usuarios,
 * así como cerrar la sesión.
 */
@Controller
public class AdminController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;
    
    @Autowired
    private com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader springFXMLLoader;

    /** Etiqueta que muestra el saludo al administrador */
    @FXML
    private Label lblAdministrador;

    /**
     * Inicializa la vista mostrando el nombre del usuario logueado.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = stageManager.getLoggedUser();
        if (user != null) {
            lblAdministrador.setText("Hola, " + user.getNombre());
        }
    }
    
    /**
     * Redirige a la vista de creación de usuarios.
     */
    @FXML
    private void anadirUsuario() {
        stageManager.switchScene(FxmlView.ANADIRUSUARIO);
    }
    
    /**
     * Redirige a la vista de edición de usuarios.
     * Se guarda previamente el usuario seleccionado.
     */
    @FXML
    private void editarUsuario() {

        User usuario = stageManager.getLoggedUser();
        stageManager.setUsuarioSeleccionado(usuario);

        stageManager.switchScene(FxmlView.MODIFICARUSUARIO);
    }
    
    /**
     * Redirige a la vista de eliminación de usuarios.
     */
    @FXML
    private void eliminarUsuario() {
        stageManager.switchScene(FxmlView.ELIMINARUSUARIO);
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
     * Cierra la sesión del usuario actual y vuelve al login.
     * 
     * @param event evento de acción del botón
     */
    @FXML
    private void cerrarSesion(ActionEvent event) {
        stageManager.switchScene(FxmlView.LOGIN);
    }
}