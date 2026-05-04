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

/**
 * Controlador encargado de gestionar la creación de una
 * Formación en Centro de Trabajo (FE).
 * 
 * Permite seleccionar un estudiante, un tutor de empresa,
 * introducir los datos de la empresa, fechas y estado,
 * y guardar la FE en el sistema.
 */
@Controller
public class CrearFCTController {

    /** Selector de estudiantes */
    @FXML private ChoiceBox<User> cbAlumno;

    /** Selector de tutores de empresa */
    @FXML private ChoiceBox<User> cbTutor;

    /** Campo de texto para el nombre de la empresa */
    @FXML private TextField txtEmpresa;

    /** Fecha de inicio de la FE */
    @FXML private DatePicker fechaInicio;

    /** Fecha de fin de la FE */
    @FXML private DatePicker fechaFin;

    /** Selector del estado de la FE */
    @FXML private ChoiceBox<Estado> cbEstado;

    /** Servicio de gestión de usuarios */
    @Autowired private UserService userService;

    /** Repositorio de FE */
    @Autowired private FormacionEmpresaRepository formacionRepo;

    /** Gestor de navegación entre vistas */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la vista cargando los datos necesarios en los selectores.
     * 
     * Filtra los usuarios para obtener estudiantes y tutores de empresa,
     * y configura la visualización de los ChoiceBox.
     */
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

        // Conversor para mostrar el nombre del usuario en los ChoiceBox
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

        // Carga los valores del enum Estado
        cbEstado.getItems().addAll(Estado.values());
    }

    /**
     * Valida los datos introducidos y crea una nueva FE.
     * 
     * Si los datos son correctos, se guarda en la base de datos
     * y se muestra un mensaje de confirmación.
     */
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

    /**
     * Vuelve al panel del profesor.
     */
    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.PROFESOR);
    }

    /**
     * Muestra un mensaje emergente al usuario.
     * 
     * @param t título de la alerta
     * @param m mensaje a mostrar
     */
    private void alerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}