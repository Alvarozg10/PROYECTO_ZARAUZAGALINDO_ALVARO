package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controlador encargado de mostrar y gestionar el listado de estudiantes.
 * 
 * Permite visualizar en una tabla los usuarios con perfil de estudiante,
 * mostrando su información básica como nombre, apellidos, fecha de nacimiento,
 * género y teléfono.
 */
@Controller
public class GestionarEstudiantesController implements Initializable {

    /** Tabla que muestra los estudiantes */
    @FXML private TableView<User> tablaEstudiantes;

    @FXML private TableColumn<User, String> colNombre;
    @FXML private TableColumn<User, String> colApellidos;
    @FXML private TableColumn<User, String> colFecha;
    @FXML private TableColumn<User, String> colGenero;
    @FXML private TableColumn<User, String> colTelefono;

    /** Servicio de gestión de usuarios */
    @Autowired
    private UserService userService;

    /** Gestor de navegación entre vistas */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la tabla configurando las columnas
     * y cargando los datos de los estudiantes.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colNombre.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getNombre())
        );

        colApellidos.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getApellidos())
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

        colTelefono.setCellValueFactory(data ->
            new SimpleStringProperty(
                data.getValue().getTelefono() != null ?
                data.getValue().getTelefono() : "-"
            )
        );

        tablaEstudiantes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cargarDatos();
    }

    /**
     * Carga los usuarios con perfil de estudiante desde la base de datos.
     * 
     * Filtra la lista de usuarios para obtener únicamente aquellos
     * cuyo perfil es "ESTUDIANTE".
     */
    private void cargarDatos() {

        List<User> estudiantes = userService.findAll()
                .stream()
                .filter(u -> "ESTUDIANTE".equalsIgnoreCase(u.getPerfil()))
                .toList();

        tablaEstudiantes.setItems(
            FXCollections.observableArrayList(estudiantes)
        );
    }

    /**
     * Vuelve al panel principal del profesor.
     */
    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.PROFESOR);
    }
}