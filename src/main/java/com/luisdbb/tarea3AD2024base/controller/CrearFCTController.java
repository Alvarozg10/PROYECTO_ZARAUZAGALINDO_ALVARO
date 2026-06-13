package com.luisdbb.tarea3AD2024base.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Empresa;
import com.luisdbb.tarea3AD2024base.modelo.Estado;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.EmpresaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

/**
 * Controlador encargado de gestionar la creación de una
 * Formación en Empresa (FE).
 */
@Controller
public class CrearFCTController {

    @FXML
    private ChoiceBox<User> cbAlumno;

    @FXML
    private ChoiceBox<User> cbTutor;

    @FXML
    private ChoiceBox<Empresa> cbEmpresa;

    @FXML
    private DatePicker fechaInicio;

    @FXML
    private DatePicker fechaFin;

    @FXML
    private ChoiceBox<Estado> cbEstado;

    @Autowired
    private UserService userService;

    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    public void initialize() {

        List<User> usuarios = userService.findAll();

        // Alumnos
        List<User> alumnos = usuarios.stream()
                .filter(u -> "ESTUDIANTE".equalsIgnoreCase(u.getPerfil()))
                .collect(Collectors.toList());

        cbAlumno.setItems(
                FXCollections.observableArrayList(alumnos)
        );

        // Tutores empresa
        List<User> tutores = usuarios.stream()
                .filter(u -> "TUTOR_EMPRESA".equalsIgnoreCase(u.getPerfil()))
                .collect(Collectors.toList());

        cbTutor.setItems(
                FXCollections.observableArrayList(tutores)
        );

        // Empresas
        cbEmpresa.setItems(
                FXCollections.observableArrayList(
                        empresaRepository.findAll()
                )
        );

        // Estados
        cbEstado.getItems().addAll(Estado.values());

        // Mostrar alumnos
        cbAlumno.setConverter(new StringConverter<>() {

            @Override
            public String toString(User u) {

                if (u == null) {
                    return "";
                }

                return u.getNombre()
                        + " "
                        + u.getApellidos()
                        + " - "
                        + u.getCiclo()
                        + " "
                        + u.getCurso();
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });

        // Mostrar tutores
        cbTutor.setConverter(new StringConverter<>() {

            @Override
            public String toString(User u) {

                if (u == null) {
                    return "";
                }

                return u.getNombre()
                        + " "
                        + u.getApellidos();
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });

        // Mostrar empresas
        cbEmpresa.setConverter(new StringConverter<>() {

            @Override
            public String toString(Empresa empresa) {

                if (empresa == null) {
                    return "";
                }

                return empresa.getNombre();
            }

            @Override
            public Empresa fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    private void guardarFCT() {

        if (cbAlumno.getValue() == null
                || cbTutor.getValue() == null
                || cbEmpresa.getValue() == null
                || fechaInicio.getValue() == null
                || fechaFin.getValue() == null
                || cbEstado.getValue() == null) {

            alerta("Error", "Rellena todos los campos");
            return;
        }

        FormacionEmpresa f = new FormacionEmpresa();

        f.setEstudiante(cbAlumno.getValue());
        f.setTutor(cbTutor.getValue());
        f.setEmpresa(cbEmpresa.getValue());

        f.setFechaInicio(
                Date.valueOf(fechaInicio.getValue())
        );

        f.setFechaFin(
                Date.valueOf(fechaFin.getValue())
        );

        f.setEstado(
                cbEstado.getValue()
        );

        formacionRepo.save(f);

        alerta(
                "Correcto",
                "FE creada correctamente"
        );

        limpiarCampos();
    }

    private void limpiarCampos() {

        cbAlumno.setValue(null);
        cbTutor.setValue(null);
        cbEmpresa.setValue(null);

        fechaInicio.setValue(null);
        fechaFin.setValue(null);

        cbEstado.setValue(null);
    }

    @FXML
    private void volver() {

        stageManager.switchScene(
                FxmlView.PROFESOR
        );
    }

    private void alerta(String titulo, String mensaje) {

        Alert a = new Alert(
                Alert.AlertType.INFORMATION
        );

        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);

        a.showAndWait();
    }
}