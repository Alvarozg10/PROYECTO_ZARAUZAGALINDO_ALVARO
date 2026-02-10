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

@SpringBootApplication
public class Tarea3Ad2024baseApplication extends Application {

    protected ConfigurableApplicationContext springContext;
    protected StageManager stageManager;

    @Override
    public void init() throws Exception {
        springContext = springBootApplicationContext();
    }

    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stageManager = springContext.getBean(StageManager.class, primaryStage);
        displayInitialScene();
    }

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

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), splashRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            PauseTransition pause = new PauseTransition(Duration.seconds(2.0));

            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), splashRoot);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

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


    private ConfigurableApplicationContext springBootApplicationContext() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Tarea3Ad2024baseApplication.class);
        String[] args = getParameters().getRaw().stream().toArray(String[]::new);
        return builder.run(args);
    }

}

