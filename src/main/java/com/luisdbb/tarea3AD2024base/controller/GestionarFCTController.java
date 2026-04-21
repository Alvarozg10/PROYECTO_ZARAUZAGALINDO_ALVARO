package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;

@Controller
public class GestionarFCTController implements Initializable {

    @FXML private TableView<FormacionEmpresa> tablaFCT;

    @FXML private TableColumn<FormacionEmpresa, String> colAlumno;
    @FXML private TableColumn<FormacionEmpresa, String> colTutor;
    @FXML private TableColumn<FormacionEmpresa, String> colEmpresa;
    @FXML private TableColumn<FormacionEmpresa, String> colInicio;
    @FXML private TableColumn<FormacionEmpresa, String> colFin;
    @FXML private TableColumn<FormacionEmpresa, String> colEstado;

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colAlumno.setCellValueFactory(data -> {
            User u = data.getValue().getEstudiante();
            return new SimpleStringProperty(
                u != null ? u.getNombre() + " " + u.getApellidos() : "Sin alumno"
            );
        });

        colTutor.setCellValueFactory(data -> {
            User u = data.getValue().getTutor();
            return new SimpleStringProperty(
                u != null ? u.getNombre() : "Sin tutor"
            );
        });

        colEmpresa.setCellValueFactory(data -> {
            String empresa = data.getValue().getEmpresa();
            return new SimpleStringProperty(
                empresa != null ? empresa : "Sin empresa"
            );
        });

        colInicio.setCellValueFactory(data -> {
            var f = data.getValue();
            return new SimpleStringProperty(
                f.getFechaInicio() != null ? f.getFechaInicio().toString() : "-"
            );
        });

        colFin.setCellValueFactory(data -> {
            var f = data.getValue();
            return new SimpleStringProperty(
                f.getFechaFin() != null ? f.getFechaFin().toString() : "-"
            );
        });

        colEstado.setCellValueFactory(data -> {
            var f = data.getValue();
            return new SimpleStringProperty(
                f.getEstado() != null ? f.getEstado() : "Sin estado"
            );
        });

        cargarTabla();
    }

    private void cargarTabla() {
        tablaFCT.setItems(
            FXCollections.observableArrayList(
                userService.findAllFormaciones()
            )
        );
    }

    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.PROFESOR);
    }
}