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

public class StageManager {

    private static final Logger LOG = getLogger(StageManager.class);
    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;
    private User loggedUser;
    private User usuarioSeleccionado;

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    public void switchScene(final FxmlView view) {
        Parent root = loadViewNodeHierarchy(view.getFxmlFile());
        show(root, view.getTitle());
    }

    private void show(final Parent root, String title) {

        Scene scene = new Scene(root);

        scene.getStylesheets().add(
            getClass().getResource("/styles/app.css").toExternalForm()
        );

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);

        primaryStage.setWidth(600);
        primaryStage.setHeight(500);
        primaryStage.setResizable(false);

        primaryStage.centerOnScreen();
        primaryStage.show();
    }

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

    private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }

    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setUsuarioSeleccionado(User user) {
        this.usuarioSeleccionado = user;
    }

    public User getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }
}
