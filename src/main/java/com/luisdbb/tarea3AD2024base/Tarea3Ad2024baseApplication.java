package com.luisdbb.tarea3AD2024base;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Clase principal de la aplicación.
 */
@SpringBootApplication
public class Tarea3Ad2024baseApplication extends Application {

    private ConfigurableApplicationContext springContext;

    private StageManager stageManager;

    @Override
    public void init() throws Exception {

        springContext = springBootApplicationContext();
    }

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        stageManager =
                springContext.getBean(
                        StageManager.class,
                        primaryStage
                );

        displayInitialScene();
    }

    /**
     * Splash screen inicial.
     */
    private void displayInitialScene() {

        try {

            Parent splashRoot =
                    FXMLLoader.load(
                            getClass().getResource(
                                    "/fxml/Inicio.fxml"
                            )
                    );

            double targetWidth = 400.0;
            double targetHeight = 250.0;

            Scene splashScene =
                    new Scene(
                            splashRoot,
                            targetWidth,
                            targetHeight
                    );

            Stage splashStage = new Stage();

            splashStage.setScene(splashScene);
            splashStage.setResizable(false);
            splashStage.centerOnScreen();
            splashStage.show();

            FadeTransition fadeIn =
                    new FadeTransition(
                            Duration.millis(300),
                            splashRoot
                    );

            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            PauseTransition pause =
                    new PauseTransition(
                            Duration.seconds(2)
                    );

            FadeTransition fadeOut =
                    new FadeTransition(
                            Duration.millis(300),
                            splashRoot
                    );

            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            SequentialTransition seq =
                    new SequentialTransition(
                            fadeIn,
                            pause,
                            fadeOut
                    );

            seq.setOnFinished(event -> {

                try {

                    splashStage.close();

                    stageManager.switchScene(
                            FxmlView.LOGIN
                    );

                } catch (Exception e) {

                    e.printStackTrace();
                }
            });

            seq.play();

        } catch (Exception e) {

            e.printStackTrace();

            try {

                stageManager.switchScene(
                        FxmlView.LOGIN
                );

            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }
    }

    /**
     * Arranque Spring Boot.
     */
    private ConfigurableApplicationContext springBootApplicationContext() {

        SpringApplicationBuilder builder =
                new SpringApplicationBuilder(
                        Tarea3Ad2024baseApplication.class
                );

        String[] args =
                getParameters()
                        .getRaw()
                        .toArray(
                                new String[0]
                        );

        return builder.run(args);
    }

    /**
     * Cierre correcto de JavaFX + Spring.
     */
    @Override
    public void stop() throws Exception {

        if (springContext != null) {

            springContext.close();
        }

        System.exit(0);
    }
}