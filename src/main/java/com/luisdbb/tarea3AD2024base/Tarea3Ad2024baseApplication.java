package com.luisdbb.tarea3AD2024base;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Application;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Clase principal de la aplicación.
 * 
 * Integra Spring Boot con JavaFX, inicializando el contexto de Spring
 * y gestionando el arranque de la interfaz gráfica.
 * 
 * Además, muestra una pantalla inicial (splash screen) con animación
 * antes de cargar la vista de login.
 */
@SpringBootApplication
public class Tarea3Ad2024baseApplication extends Application {

    /** Contexto de Spring */
    protected ConfigurableApplicationContext springContext;

    /** Gestor de escenas JavaFX */
    protected StageManager stageManager;

    /**
     * Inicializa el contexto de Spring antes de arrancar la aplicación.
     */
    @Override
    public void init() throws Exception {
        springContext = springBootApplicationContext();
    }

    /**
     * Método principal de ejecución de la aplicación.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }

    /**
     * Inicia la aplicación JavaFX y configura el Stage principal.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stageManager = springContext.getBean(StageManager.class, primaryStage);
        displayInitialScene();
    }

    /**
     * Muestra la pantalla inicial (splash screen) con animación.
     * 
     * Se aplica una transición de entrada, pausa y salida,
     * tras la cual se carga la vista de login.
     */
    protected void displayInitialScene() {
        try {
            Parent splashRoot = FXMLLoader.load(getClass().getResource("/fxml/Inicio.fxml"));

            double targetWidth = 400.0;
            double targetHeight = 250.0;

            Scene splashScene = new Scene(splashRoot, targetWidth, targetHeight);

            Stage splashStage = new Stage();
            splashStage.setScene(splashScene);
            splashStage.setResizable(false);
            splashStage.centerOnScreen();
            splashStage.show();

            // Animación de entrada
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), splashRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            // Pausa en pantalla
            PauseTransition pause = new PauseTransition(Duration.seconds(2.0));

            // Animación de salida
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), splashRoot);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            // Secuencia de animaciones
            SequentialTransition seq = new SequentialTransition(fadeIn, pause, fadeOut);

            seq.setOnFinished(evt -> {
                try {
                    splashStage.close();

                    try {
                        Stage primary = (Stage) splashRoot.getScene().getWindow();
                        primary.setWidth(targetWidth);
                        primary.setHeight(targetHeight);
                        primary.centerOnScreen();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    // Carga de la pantalla de login
                    stageManager.switchScene(FxmlView.LOGIN);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            seq.play();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                stageManager.switchScene(FxmlView.LOGIN);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Inicializa el contexto de Spring Boot.
     * 
     * @return contexto de la aplicación
     */
    private ConfigurableApplicationContext springBootApplicationContext() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Tarea3Ad2024baseApplication.class);
        String[] args = getParameters().getRaw().stream().toArray(String[]::new);
        return builder.run(args);
    }
}
