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
 * Controlador encargado de mostrar las formaciones en empresa (FCT).
 */
@Controller
public class ConsultarFCTController implements Initializable {

    /** Tabla principal */
    @FXML
    private TableView<FormacionEmpresa> tablaFCT;

    @FXML
    private TableColumn<FormacionEmpresa, String> colAlumno;

    @FXML
    private TableColumn<FormacionEmpresa, String> colTutor;

    @FXML
    private TableColumn<FormacionEmpresa, String> colEmpresa;

    @FXML
    private TableColumn<FormacionEmpresa, String> colInicio;

    @FXML
    private TableColumn<FormacionEmpresa, String> colFin;

    @FXML
    private TableColumn<FormacionEmpresa, String> colEstado;
    
    @FXML private TableColumn<FormacionEmpresa, String> colCurso;
    @FXML private TableColumn<FormacionEmpresa, String> colCiclo;

    /** Repositorio FCT */
    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    /** Navegación */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la tabla.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tablaFCT.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        // Alumno
        colAlumno.setCellValueFactory(data -> {

            if (data.getValue().getEstudiante() == null) {
                return new SimpleStringProperty("Sin alumno");
            }

            var alumno = data.getValue().getEstudiante();

            String texto = alumno.getNombre();

            return new SimpleStringProperty(texto);
        });

        // Tutor
        colTutor.setCellValueFactory(data -> {

            if (data.getValue().getTutor() == null) {
                return new SimpleStringProperty("Sin tutor");
            }

            var tutor = data.getValue().getTutor();

            return new SimpleStringProperty(
                    tutor.getNombre() + " " + tutor.getApellidos()
            );
        });

        // Empresa
        colEmpresa.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getEmpresa()
                )
        );

        // Fecha inicio
        colInicio.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getFechaInicio().toString()
                )
        );

        // Fecha fin
        colFin.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getFechaFin().toString()
                )
        );

        // Estado
        colEstado.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getEstado().toString()
                )
        );
        
        // Curso
        colCurso.setCellValueFactory(data ->
        new SimpleStringProperty(
            data.getValue().getEstudiante() != null &&
            data.getValue().getEstudiante().getCurso() != null
                ? data.getValue().getEstudiante().getCurso().toString()
                : "-"
        )
    );
        
        // Ciclo
	    colCiclo.setCellValueFactory(data ->
	        new SimpleStringProperty(
	            data.getValue().getEstudiante() != null &&
	            data.getValue().getEstudiante().getCiclo() != null
	                ? data.getValue().getEstudiante().getCiclo().toString()
	                : "-"
	        )
	    );

        cargarDatos();
    }

    /**
     * Carga las FCT en la tabla.
     */
    private void cargarDatos() {

        tablaFCT.setItems(
                FXCollections.observableArrayList(
                        formacionRepo.findAll()
                )
        );
    }

    /**
     * Vuelve al menú del profesor.
     */
    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.PROFESOR);
    }
}