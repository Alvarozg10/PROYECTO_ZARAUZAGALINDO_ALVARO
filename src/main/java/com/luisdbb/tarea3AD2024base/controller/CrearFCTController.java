package com.luisdbb.tarea3AD2024base.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estado;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * Controlador encargado de gestionar la creación de una
 * Formación en Centro de Trabajo (FCT).
 */
@Controller
public class CrearFCTController {

    @FXML
    private ChoiceBox<User> cbAlumno;

    @FXML
    private ChoiceBox<User> cbTutor;

    @FXML
    private TextField txtEmpresa;

    @FXML
    private DatePicker fechaInicio;

    @FXML
    private DatePicker fechaFin;

    @FXML
    private ChoiceBox<Estado> cbEstado;

    @FXML
    private ChoiceBox<String> cbCurso;

    @FXML
    private ChoiceBox<String> cbCiclo;

    @Autowired
    private UserService userService;

    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    public void initialize() {

        List<User> usuarios = userService.findAll();

        // Filtrar alumnos
        List<User> alumnos = usuarios.stream()
                .filter(u -> "ESTUDIANTE".equalsIgnoreCase(u.getPerfil()))
                .collect(Collectors.toList());

        cbAlumno.setItems(FXCollections.observableArrayList(alumnos));

        // Filtrar tutores
        List<User> tutores = usuarios.stream()
                .filter(u -> "TUTOR_EMPRESA".equalsIgnoreCase(u.getPerfil()))
                .collect(Collectors.toList());

        cbTutor.setItems(FXCollections.observableArrayList(tutores));

        // Mostrar info completa del alumno
        cbAlumno.setConverter(new StringConverter<>() {

            @Override
            public String toString(User u) {

                if (u == null) {
                    return "";
                }

                return u.getNombre() + " "
                        + u.getApellidos()
                        + " - "
                        + u.getCiclo()
                        + " "
                        + u.getCurso();
            }

            @Override
            public User fromString(String s) {
                return null;
            }
        });

        // Mostrar tutor
        cbTutor.setConverter(new StringConverter<>() {

            @Override
            public String toString(User u) {

                if (u == null) {
                    return "";
                }

                return u.getNombre() + " " + u.getApellidos();
            }

            @Override
            public User fromString(String s) {
                return null;
            }
        });

        cbCurso.getItems().addAll(
        	    "PRIMERO",
        	    "SEGUNDO"
        	);

        	cbCiclo.getItems().addAll(
        	    "DAM",
        	    "DAW"
        	);

        cbEstado.getItems().addAll(Estado.values());
    }

    @FXML
    private void guardarFCT() {

        if (cbAlumno.getValue() == null ||
            cbTutor.getValue() == null ||
            txtEmpresa.getText().isEmpty() ||
            fechaInicio.getValue() == null ||
            fechaFin.getValue() == null ||
            cbEstado.getValue() == null) {

            alerta("Error", "Rellena todos los campos");
            return;
        }

        FormacionEmpresa f = new FormacionEmpresa();

        f.setEstudiante(cbAlumno.getValue());
        f.setTutor(cbTutor.getValue());

        f.setEmpresa(txtEmpresa.getText());

        f.setFechaInicio(Date.valueOf(fechaInicio.getValue()));
        f.setFechaFin(Date.valueOf(fechaFin.getValue()));

        f.setEstado(cbEstado.getValue());

        formacionRepo.save(f);

        alerta("OK", "FCT creada correctamente");

        limpiarCampos();
    }

    private void limpiarCampos() {

        cbAlumno.setValue(null);
        cbTutor.setValue(null);

        txtEmpresa.clear();

        fechaInicio.setValue(null);
        fechaFin.setValue(null);

        cbEstado.setValue(null);

        cbCurso.setValue(null);
        cbCiclo.setValue(null);
    }

    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.PROFESOR);
    }

    private void alerta(String t, String m) {

        Alert a = new Alert(Alert.AlertType.INFORMATION);

        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);

        a.showAndWait();
    }
}