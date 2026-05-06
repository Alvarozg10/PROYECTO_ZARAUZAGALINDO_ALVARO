package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;

/**
 * Controlador encargado de gestionar el sistema de ayuda.
 * 
 * Utiliza el componente WebView para cargar contenido HTML
 * desde los recursos de la aplicación.
 */
@Controller
public class AyudaController implements Initializable {

    /** Componente WebView para mostrar la ayuda */
    @FXML
    private WebView webView;

    /**
     * Inicializa la vista de ayuda cargando el archivo HTML.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cargarAyuda();

        // Detecta la tecla F1
        webView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.F1) {

                // Recarga la ayuda
                cargarAyuda();

                event.consume();
            }
        });
    }

    /**
     * Carga el contenido HTML en el WebView.
     */
    private void cargarAyuda() {
        try {

            String url = getClass()
                    .getResource("/ayuda/help.html")
                    .toExternalForm();

            webView.getEngine().load(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}