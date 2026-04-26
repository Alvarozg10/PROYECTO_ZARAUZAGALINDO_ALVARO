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

@Controller
public class AlumnosTutorController implements Initializable {

    @FXML private TableView<User> tablaAlumnos;

    @FXML private TableColumn<User, String> colNombre;
    @FXML private TableColumn<User, String> colApellidos;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colTelefono;
    @FXML private TableColumn<User, String> colFecha;
    @FXML private TableColumn<User, String> colGenero;

    @Autowired
    private FormacionEmpresaRepository formacionRepo;

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

    private void cargarDatos() {

        User tutor = stageManager.getLoggedUser();

        List<User> alumnos = formacionRepo.findAll()
                .stream()
                .filter(f -> f.getTutor() != null &&
                             f.getTutor().getIdUsuario().equals(tutor.getIdUsuario()))
                .map(FormacionEmpresa::getEstudiante)
                .distinct() // 🔥 evita duplicados
                .toList();

        tablaAlumnos.setItems(
            FXCollections.observableArrayList(alumnos)
        );
    }

    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.TUTOR_EMPRESA);
    }
}