package com.luisdbb.tarea3AD2024base.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.*;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

@Controller
public class CrearFCTController {

    @FXML private ChoiceBox<User> cbAlumno;
    @FXML private ChoiceBox<User> cbTutor;
    @FXML private TextField txtEmpresa;
    @FXML private DatePicker fechaInicio;
    @FXML private DatePicker fechaFin;
    @FXML private ChoiceBox<String> cbEstado;

    @Autowired private UserService userService;
    @Autowired private FormacionEmpresaRepository formacionRepo;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    public void initialize() {

        List<User> usuarios = userService.findAll();

        List<User> alumnos = usuarios.stream()
                .filter(u -> "ESTUDIANTE".equalsIgnoreCase(u.getPerfil()))
                .collect(Collectors.toList());

        cbAlumno.setItems(FXCollections.observableArrayList(alumnos));

        List<User> tutores = usuarios.stream()
                .filter(u -> "TUTOR_EMPRESA".equalsIgnoreCase(u.getPerfil()))
                .collect(Collectors.toList());

        cbTutor.setItems(FXCollections.observableArrayList(tutores));

        cbAlumno.setConverter(new StringConverter<>() {
            @Override
            public String toString(User u) {
                return u != null ? u.getNombre() : "";
            }
            @Override
            public User fromString(String s) { return null; }
        });

        cbTutor.setConverter(new StringConverter<>() {
            @Override
            public String toString(User u) {
                return u != null ? u.getNombre() : "";
            }
            @Override
            public User fromString(String s) { return null; }
        });

        cbEstado.getItems().addAll("PENDIENTE", "EN_CURSO", "FINALIZADA");
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