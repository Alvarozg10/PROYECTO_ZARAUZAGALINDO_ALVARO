package com.luisdbb.tarea3AD2024base.config;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Componente encargado de cargar vistas FXML integradas con Spring.
 * 
 * Permite que los controladores definidos en los archivos FXML
 * sean gestionados por el contenedor de Spring, habilitando así
 * la inyección de dependencias (@Autowired) en ellos.
 */
@Component
public class SpringFXMLLoader {

    /** ResourceBundle para internacionalización */
    private final ResourceBundle resourceBundle;

    /** Contexto de Spring */
    private final ApplicationContext context;

    /**
     * Constructor que inicializa el loader con el contexto de Spring
     * y el ResourceBundle.
     * 
     * @param context contexto de la aplicación Spring
     * @param resourceBundle recursos de la aplicación
     */
    @Autowired
    public SpringFXMLLoader(ApplicationContext context, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.context = context;
    }

    /**
     * Carga un archivo FXML utilizando Spring para la gestión de controladores.
     * 
     * Establece un controllerFactory personalizado para que los controladores
     * sean instanciados por Spring en lugar de JavaFX.
     * 
     * @param fxmlPath ruta del archivo FXML
     * @return nodo raíz de la vista cargada
     * @throws IOException si ocurre un error al cargar el archivo
     */
    public Parent load(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/" + fxmlPath), 
                resourceBundle
        );

        // Permite que Spring gestione los controladores
        loader.setControllerFactory(context::getBean); 

        return loader.load();
    }
}