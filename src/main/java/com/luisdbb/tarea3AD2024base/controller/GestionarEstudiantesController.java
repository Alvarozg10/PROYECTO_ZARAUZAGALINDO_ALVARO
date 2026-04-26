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

@Controller
public class GestionarEstudiantesController implements Initializable {

    @FXML private TableView<User> tablaEstudiantes;

    @FXML private TableColumn<User, String> colNombre;
    @FXML private TableColumn<User, String> colApellidos;
    @FXML private TableColumn<User, String> colFecha;
    @FXML private TableColumn<User, String> colGenero;
    @FXML private TableColumn<User, String> colTelefono;

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private StageManager stageManager;

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

        // 🔥 EVITA COLUMNA VACÍA
        tablaEstudiantes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cargarDatos();
    }

    private void cargarDatos() {

        List<User> estudiantes = userService.findAll()
                .stream()
                .filter(u -> "ESTUDIANTE".equalsIgnoreCase(u.getPerfil()))
                .toList();

        tablaEstudiantes.setItems(
            FXCollections.observableArrayList(estudiantes)
        );
    }

    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.PROFESOR);
    }
}