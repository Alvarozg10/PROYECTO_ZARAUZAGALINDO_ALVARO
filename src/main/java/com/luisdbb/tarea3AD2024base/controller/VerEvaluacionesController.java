package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Evaluacion;
import com.luisdbb.tarea3AD2024base.repositorios.EvaluacionRepository;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@Controller
public class VerEvaluacionesController {

    @FXML
    private TableView<Evaluacion> tablaEvaluaciones;

    @FXML
    private TableColumn<Evaluacion, String> colAlumno;

    @FXML
    private TableColumn<Evaluacion, String> colEmpresa;

    @FXML
    private TableColumn<Evaluacion, String> colTutor;

    @FXML
    private TableColumn<Evaluacion, String> colMedia;

    @FXML
    private TableColumn<Evaluacion, String> colComentario;

    @Autowired
    private EvaluacionRepository evaluacionRepo;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    public void initialize() {

        // Alumno
        colAlumno.setCellValueFactory(data -> {

            if (data.getValue().getFe() == null
                    || data.getValue().getFe().getEstudiante() == null) {

                return new SimpleStringProperty("-");
            }

            return new SimpleStringProperty(

                    data.getValue()
                            .getFe()
                            .getEstudiante()
                            .getNombre()

                            + " "

                            + data.getValue()
                            .getFe()
                            .getEstudiante()
                            .getApellidos()
            );
        });

        // Empresa
        colEmpresa.setCellValueFactory(data -> {

            if (data.getValue().getFe() == null
                    || data.getValue().getFe().getEmpresa() == null) {

                return new SimpleStringProperty("-");
            }

            return new SimpleStringProperty(

                    data.getValue()
                            .getFe()
                            .getEmpresa()
                            .getNombre()
            );
        });

        // Tutor
        colTutor.setCellValueFactory(data -> {

            if (data.getValue().getFe() == null
                    || data.getValue().getFe().getTutor() == null) {

                return new SimpleStringProperty("-");
            }

            return new SimpleStringProperty(

                    data.getValue()
                            .getFe()
                            .getTutor()
                            .getNombre()

                            + " "

                            + data.getValue()
                            .getFe()
                            .getTutor()
                            .getApellidos()
            );
        });

        // Media
        colMedia.setCellValueFactory(data ->

                new SimpleStringProperty(

                        String.format(
                                "%.2f",
                                data.getValue().getMedia()
                        )
                )
        );

        // Comentario
        colComentario.setCellValueFactory(data ->

                new SimpleStringProperty(

                        data.getValue().getObservaciones() != null

                                ? data.getValue().getObservaciones()

                                : "-"
                )
        );

        tablaEvaluaciones.setItems(

                FXCollections.observableArrayList(
                        evaluacionRepo.findAll()
                )
        );
    }

    @FXML
    private void verDetalles() {

        Evaluacion e = tablaEvaluaciones
                .getSelectionModel()
                .getSelectedItem();

        if (e == null) {

            Alert a = new Alert(
                    Alert.AlertType.WARNING
            );

            a.setTitle("Aviso");
            a.setHeaderText(null);
            a.setContentText(
                    "Selecciona una evaluación"
            );

            a.showAndWait();

            return;
        }

        Alert detalle = new Alert(
                Alert.AlertType.INFORMATION
        );

        detalle.setTitle(
                "Detalle evaluación"
        );

        detalle.setHeaderText(

                e.getFe()
                        .getEstudiante()
                        .getNombre()

                        + " "

                        + e.getFe()
                        .getEstudiante()
                        .getApellidos()
        );

        detalle.setContentText(

                "Tutor empresa: "
                        + e.getFe()
                        .getTutor()
                        .getNombre()

                        + " "

                        + e.getFe()
                        .getTutor()
                        .getApellidos()

                        + "\n\nPuntualidad: "
                        + e.getPuntualidad()

                        + "\nResponsabilidad: "
                        + e.getResponsabilidad()

                        + "\nTrabajo en equipo: "
                        + e.getTrabajoEquipo()

                        + "\nIniciativa: "
                        + e.getIniciativa()

                        + "\n\nMedia: "
                        + String.format(
                        "%.2f",
                        e.getMedia()
                )

                        + "\n\nObservaciones:\n"
                        + e.getObservaciones()
        );

        detalle.showAndWait();
    }

    @FXML
    private void volver() {

        stageManager.switchScene(
                FxmlView.PROFESOR
        );
    }
}