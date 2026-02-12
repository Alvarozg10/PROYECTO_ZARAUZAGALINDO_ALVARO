package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

        colAlumno.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getEstudiante().getNombre() + " " +
                data.getValue().getEstudiante().getApellidos()
            )
        );

        colTutor.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getTutorEmpresa().getNombre()
            )
        );

        colEmpresa.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getEmpresa().getNombre()
            )
        );

        colInicio.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getFechaInicio().toString()
            )
        );

        colFin.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getFechaFin().toString()
            )
        );

        colEstado.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getEstado()
            )
        );

        tablaFCT.setItems(
            FXCollections.observableArrayList(
                userService.findAllFormaciones()
            )
        );
    }

    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.PROFESOR1);
    }
}
