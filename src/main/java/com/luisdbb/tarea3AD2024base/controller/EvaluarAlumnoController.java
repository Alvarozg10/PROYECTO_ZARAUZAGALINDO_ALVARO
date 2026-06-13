package com.luisdbb.tarea3AD2024base.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Evaluacion;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.EvaluacionRepository;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;

@Controller
public class EvaluarAlumnoController {

    @FXML
    private ChoiceBox<FormacionEmpresa> cbAlumno;

    @FXML
    private ChoiceBox<Integer> cbPuntualidad;

    @FXML
    private ChoiceBox<Integer> cbResponsabilidad;

    @FXML
    private ChoiceBox<Integer> cbTrabajoEquipo;

    @FXML
    private ChoiceBox<Integer> cbIniciativa;

    @FXML
    private TextArea txtObservaciones;

    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    @Autowired
    private EvaluacionRepository evaluacionRepo;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    public void initialize() {

        User tutor = stageManager.getLoggedUser();

        List<FormacionEmpresa> alumnosTutor = formacionRepo.findAll()
                .stream()
                .filter(fe ->
                        fe.getTutor() != null
                        &&
                        fe.getTutor()
                          .getIdUsuario()
                          .equals(tutor.getIdUsuario()))
                .filter(fe ->
                        fe.getEvaluacion() == null)
                .collect(Collectors.toList());

        cbAlumno.setItems(
                FXCollections.observableArrayList(
                        alumnosTutor
                )
        );

        cbAlumno.setConverter(
                new StringConverter<>() {

                    @Override
                    public String toString(FormacionEmpresa fe) {

                        if (fe == null
                                || fe.getEstudiante() == null) {
                            return "";
                        }

                        return fe.getEstudiante().getNombre()
                                + " "
                                + fe.getEstudiante().getApellidos();
                    }

                    @Override
                    public FormacionEmpresa fromString(String string) {
                        return null;
                    }
                }
        );

        cbPuntualidad.getItems().addAll(
                1, 2, 3, 4, 5
        );

        cbResponsabilidad.getItems().addAll(
                1, 2, 3, 4, 5
        );

        cbTrabajoEquipo.getItems().addAll(
                1, 2, 3, 4, 5
        );

        cbIniciativa.getItems().addAll(
                1, 2, 3, 4, 5
        );
    }

    @FXML
    private void guardarEvaluacion() {

        if (cbAlumno.getValue() == null
                || cbPuntualidad.getValue() == null
                || cbResponsabilidad.getValue() == null
                || cbTrabajoEquipo.getValue() == null
                || cbIniciativa.getValue() == null) {

            alerta(
                    "Error",
                    "Completa toda la evaluación"
            );

            return;
        }

        FormacionEmpresa fe =
                cbAlumno.getValue();

        if (fe.getEvaluacion() != null) {

            alerta(
                    "Error",
                    "Este alumno ya tiene una evaluación"
            );

            return;
        }

        Evaluacion evaluacion =
                new Evaluacion();

        evaluacion.setFe(fe);

        evaluacion.setPuntualidad(
                cbPuntualidad.getValue()
        );

        evaluacion.setResponsabilidad(
                cbResponsabilidad.getValue()
        );

        evaluacion.setTrabajoEquipo(
                cbTrabajoEquipo.getValue()
        );

        evaluacion.setIniciativa(
                cbIniciativa.getValue()
        );

        evaluacion.setObservaciones(
                txtObservaciones.getText()
        );

        evaluacionRepo.save(
                evaluacion
        );

        fe.setEvaluacion(
                evaluacion
        );

        formacionRepo.save(
                fe
        );

        alerta(
                "Correcto",
                "Evaluación guardada correctamente"
        );

        limpiar();
    }

    private void limpiar() {

        cbAlumno.setValue(null);

        cbPuntualidad.setValue(null);
        cbResponsabilidad.setValue(null);
        cbTrabajoEquipo.setValue(null);
        cbIniciativa.setValue(null);

        txtObservaciones.clear();
    }

    @FXML
    private void volver() {

        stageManager.switchScene(
                FxmlView.TUTOR_EMPRESA
        );
    }

    private void alerta(
            String titulo,
            String mensaje) {

        Alert a = new Alert(
                Alert.AlertType.INFORMATION
        );

        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);

        a.showAndWait();
    }
}