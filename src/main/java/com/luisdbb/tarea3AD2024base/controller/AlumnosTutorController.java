package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controlador encargado de mostrar los alumnos asignados
 * a un tutor de empresa.
 * 
 * Obtiene las formaciones (FCT) del tutor logueado y
 * muestra los estudiantes asociados en una tabla.
 */
@Controller
public class AlumnosTutorController implements Initializable {

    /** Tabla que muestra los alumnos asignados */
    @FXML private TableView<User> tablaAlumnos;

    @FXML private TableColumn<User, String> colNombre;
    @FXML private TableColumn<User, String> colApellidos;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colTelefono;
    @FXML private TableColumn<User, String> colFecha;
    @FXML private TableColumn<User, String> colGenero;

    /** Repositorio de formaciones FCT */
    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    /** Gestor de escenas */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la tabla configurando las columnas
     * y cargando los datos correspondientes.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colNombre.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getNombre())
        );

        colApellidos.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getApellidos())
        );

        colEmail.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getEmail())
        );

        colTelefono.setCellValueFactory(data ->
            new SimpleStringProperty(
                data.getValue().getTelefono() != null ?
                data.getValue().getTelefono() : "-"
            )
        );

        colFecha.setCellValueFactory(data ->
            new SimpleStringProperty(
                data.getValue().getFechaNacimiento() != null ?
                data.getValue().getFechaNacimiento().toString() : "-"
            )
        );

        colGenero.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getGenero())
        );

        tablaAlumnos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cargarDatos();
    }

    /**
     * Carga los alumnos asignados al tutor logueado.
     * 
     * Filtra las FCT por tutor y obtiene los estudiantes asociados,
     * eliminando duplicados.
     */
    private void cargarDatos() {

        User tutor = stageManager.getLoggedUser();

        List<User> alumnos = formacionRepo.findAll()
                .stream()
                .filter(f -> f.getTutor() != null &&
                             f.getTutor().getIdUsuario().equals(tutor.getIdUsuario()))
                .map(FormacionEmpresa::getEstudiante)
                .distinct()
                .toList();

        tablaAlumnos.setItems(
            FXCollections.observableArrayList(alumnos)
        );
    }

    /**
     * Vuelve al panel principal del tutor de empresa.
     */
    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.TUTOR_EMPRESA);
    }
}