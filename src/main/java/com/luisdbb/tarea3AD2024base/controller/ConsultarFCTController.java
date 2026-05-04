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

/**
 * Controlador encargado de mostrar las formaciones en centro de trabajo (FE).
 * 
 * Permite visualizar en una tabla la información de cada FE, incluyendo
 * estudiante, tutor, empresa, fechas y estado.
 */
@Controller
public class ConsultarFCTController implements Initializable {

    /** Tabla que contiene las FE */
    @FXML private TableView<FormacionEmpresa> tablaFCT;

    @FXML private TableColumn<FormacionEmpresa, String> colAlumno;
    @FXML private TableColumn<FormacionEmpresa, String> colTutor;
    @FXML private TableColumn<FormacionEmpresa, String> colEmpresa;
    @FXML private TableColumn<FormacionEmpresa, String> colInicio;
    @FXML private TableColumn<FormacionEmpresa, String> colFin;
    @FXML private TableColumn<FormacionEmpresa, String> colEstado;

    /** Repositorio para acceder a los datos de FE */
    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    /** Gestor de navegación entre vistas */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la tabla configurando las columnas
     * y cargando los datos desde la base de datos.
     */
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
            new SimpleStringProperty(
                data.getValue().getEstado().toString()
            )
        );

        cargarDatos();
    }

    /**
     * Carga todas las FE almacenadas en la base de datos
     * y las muestra en la tabla.
     */
    private void cargarDatos() {
        tablaFCT.setItems(
            FXCollections.observableArrayList(
                formacionRepo.findAll()
            )
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