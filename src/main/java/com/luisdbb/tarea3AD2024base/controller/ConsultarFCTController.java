package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@Controller
public class ConsultarFCTController implements Initializable {

    @FXML private TableView<FormacionEmpresa> tablaFCT;

    @FXML private TableColumn<FormacionEmpresa, String> colAlumno;
    @FXML private TableColumn<FormacionEmpresa, String> colTutor;
    @FXML private TableColumn<FormacionEmpresa, String> colEmpresa;
    @FXML private TableColumn<FormacionEmpresa, String> colInicio;
    @FXML private TableColumn<FormacionEmpresa, String> colFin;
    @FXML private TableColumn<FormacionEmpresa, String> colEstado;

    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	tablaFCT.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
        colAlumno.setCellValueFactory(data ->
            new SimpleStringProperty(
                data.getValue().getEstudiante().getNombre()
            )
        );

        colTutor.setCellValueFactory(data ->
            new SimpleStringProperty(
                data.getValue().getTutor().getNombre()
            )
        );

        colEmpresa.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getEmpresa())
        );

        colInicio.setCellValueFactory(data ->
            new SimpleStringProperty(
                data.getValue().getFechaInicio().toString()
            )
        );

        colFin.setCellValueFactory(data ->
            new SimpleStringProperty(
                data.getValue().getFechaFin().toString()
            )
        );

        colEstado.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getEstado())
        );

        cargarDatos();
    }

    private void cargarDatos() {
        tablaFCT.setItems(
            FXCollections.observableArrayList(
                formacionRepo.findAll()
            )
        );
    }

    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.PROFESOR);
    }
}