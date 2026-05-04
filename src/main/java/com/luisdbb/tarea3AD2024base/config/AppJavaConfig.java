/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luisdbb.tarea3AD2024base.config;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;


/**
 * Clase de configuración de Spring para la aplicación.
 * 
 * Define los beans necesarios para la integración entre Spring y JavaFX,
 * incluyendo la carga de recursos y la gestión de escenas.
 */
@Configuration
public class AppJavaConfig {
	
    /** Loader personalizado para cargar vistas FXML con Spring */
    @Autowired 
    SpringFXMLLoader springFXMLLoader;

    /**
     * Proporciona el ResourceBundle de la aplicación.
     * 
     * Se utiliza para la internacionalización y gestión de recursos.
     * 
     * @return ResourceBundle cargado
     */
    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("Bundle");
    }
    
    /**
     * Crea el StageManager de la aplicación.
     * 
     * Se encarga de gestionar la navegación entre escenas JavaFX.
     * 
     * Se inicializa de forma lazy para asegurar que el contexto de Spring
     * esté completamente cargado antes de crear el Stage.
     * 
     * @param stage escenario principal de JavaFX
     * @return instancia de StageManager
     * @throws IOException en caso de error al cargar vistas
     */
    @Bean
    @Lazy(value = true)
    public StageManager stageManager(Stage stage) throws IOException {
        return new StageManager(springFXMLLoader, stage);
    }

}