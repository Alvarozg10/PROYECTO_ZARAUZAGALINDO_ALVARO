package com.luisdbb.tarea3AD2024base.config;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Objects;

import org.slf4j.Logger;

import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase encargada de gestionar la navegación entre vistas de la aplicación.
 * 
 * Permite cambiar escenas (FXML), mantener el usuario logueado
 * y almacenar datos compartidos entre controladores.
 * 
 * Actúa como intermediario entre la interfaz gráfica (JavaFX)
 * y la lógica de navegación del sistema.
 */
public class StageManager {

    private static final Logger LOG = getLogger(StageManager.class);

    /** Escenario principal de la aplicación */
    private final Stage primaryStage;

    /** Loader de FXML integrado con Spring */
    private final SpringFXMLLoader springFXMLLoader;

    /** Usuario actualmente logueado */
    private User loggedUser;

    /** Usuario seleccionado (para edición, etc.) */
    private User usuarioSeleccionado;

    /**
     * Constructor del StageManager.
     * 
     * @param springFXMLLoader loader de vistas FXML
     * @param stage escenario principal
     */
    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    /**
     * Cambia la escena actual a la vista indicada.
     * 
     * @param view vista a mostrar
     */
    public void switchScene(final FxmlView view) {
        Parent root = loadViewNodeHierarchy(view.getFxmlFile());
        show(root, view);
    }

    /**
     * Muestra la escena en el Stage principal.
     * 
     * Configura el título, tamaño, estilos y centrado de la ventana.
     */
    private void show(final Parent root, FxmlView view) {

        Scene scene = new Scene(root);

        scene.getStylesheets().add(
            getClass().getResource("/styles/app.css").toExternalForm()
        );

        primaryStage.setTitle(view.getTitle());
        primaryStage.setScene(scene);

        // Ajuste dinámico del tamaño según la vista
        if (view == FxmlView.LOGIN) {
            primaryStage.setWidth(600);
            primaryStage.setHeight(500);
        } else {
            primaryStage.setWidth(700);
            primaryStage.setHeight(600);
        }

        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**
     * Carga el nodo raíz de una vista FXML.
     */
    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);
            Objects.requireNonNull(rootNode, "Root FXML no puede ser null");
        } catch (Exception exception) {
            logAndExit("Error cargando FXML: " + fxmlFilePath, exception);
        }
        return rootNode;
    }

    /**
     * Registra el error y finaliza la aplicación.
     */
    private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }

    /**
     * Guarda el usuario logueado.
     */
    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    /**
     * Obtiene el usuario logueado.
     */
    public User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Guarda un usuario seleccionado (para edición, etc.).
     */
    public void setUsuarioSeleccionado(User user) {
        this.usuarioSeleccionado = user;
    }

    /**
     * Obtiene el usuario seleccionado.
     */
    public User getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }
}