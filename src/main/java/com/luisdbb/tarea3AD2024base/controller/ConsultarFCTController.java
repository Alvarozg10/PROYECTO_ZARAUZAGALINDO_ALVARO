package com.luisdbb.tarea3AD2024base.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;
import com.luisdbb.tarea3AD2024base.services.FCTService;
import com.luisdbb.tarea3AD2024base.services.InformeService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

/**
 * Controlador encargado de mostrar las Formaciones en Empresa (FE).
 * 
 * Permite consultar todas las FE registradas y exportarlas
 * a un informe PDF.
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

    @FXML
    private TableColumn<FormacionEmpresa, String> colCurso;

    @FXML
    private TableColumn<FormacionEmpresa, String> colCiclo;

    /** Servicio de informes PDF */
    @Autowired
    private InformeService informeService;
    
    @Autowired
    private FCTService fctService;

    /** Repositorio FE */
    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    /** Gestor de escenas */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la tabla configurando columnas y datos.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
        fctService.actualizarEstadosAutomaticamente();

        tablaFCT.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        // Alumno
        colAlumno.setCellValueFactory(data -> {

            if (data.getValue().getEstudiante() == null) {
                return new SimpleStringProperty("Sin alumno");
            }

            return new SimpleStringProperty(
                    data.getValue()
                        .getEstudiante()
                        .getNombre()
            );
        });

        // Tutor
        colTutor.setCellValueFactory(data -> {

            if (data.getValue().getTutor() == null) {
                return new SimpleStringProperty("Sin tutor");
            }

            var tutor = data.getValue().getTutor();

            return new SimpleStringProperty(
                    tutor.getNombre()
                    + " "
                    + tutor.getApellidos()
            );
        });

        // Empresa
        colEmpresa.setCellValueFactory(data -> {

            if (data.getValue().getEmpresa() == null) {
                return new SimpleStringProperty("-");
            }

            return new SimpleStringProperty(
                    data.getValue()
                        .getEmpresa()
                        .getNombre()
            );
        });

        // Fecha inicio
        colInicio.setCellValueFactory(data ->

        new SimpleStringProperty(

            data.getValue().getFechaInicio() != null

            ? data.getValue()
                  .getFechaInicio()
                  .toString()

            : "-"
        )
    );

        // Fecha fin
        colFin.setCellValueFactory(data ->

        new SimpleStringProperty(

            data.getValue().getFechaFin() != null

            ? data.getValue()
                  .getFechaFin()
                  .toString()

            : "-"
        )
    );

        // Estado
        colEstado.setCellValueFactory(data ->

        new SimpleStringProperty(

            data.getValue().getEstado() != null

            ? data.getValue()
                  .getEstado()
                  .toString()

            : "-"
        )
    );

        // Curso
        colCurso.setCellValueFactory(data ->
                new SimpleStringProperty(

                        data.getValue().getEstudiante() != null
                        &&
                        data.getValue().getEstudiante().getCurso() != null

                        ? data.getValue()
                              .getEstudiante()
                              .getCurso()
                              .toString()

                        : "-"
                )
        );

        // Ciclo
        colCiclo.setCellValueFactory(data ->
                new SimpleStringProperty(

                        data.getValue().getEstudiante() != null
                        &&
                        data.getValue().getEstudiante().getCiclo() != null

                        ? data.getValue()
                              .getEstudiante()
                              .getCiclo()
                              .toString()

                        : "-"
                )
        );

        cargarDatos();
    }

    /**
     * Carga todas las FE registradas.
     */
    private void cargarDatos() {

        tablaFCT.setItems(

                FXCollections.observableArrayList(
                        formacionRepo.findAll()
                )
        );
    }

    /**
     * Exporta las FE a un informe PDF.
     */
    @FXML
    private void exportarPDF() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle(
                "Guardar informe PDF"
        );

        fileChooser.getExtensionFilters().add(

                new FileChooser.ExtensionFilter(
                        "PDF Files",
                        "*.pdf"
                )
        );

        fileChooser.setInitialFileName(
                "informe_fe.pdf"
        );

        File archivo = fileChooser.showSaveDialog(
                tablaFCT.getScene().getWindow()
        );

        if (archivo == null) {
            return;
        }

        try {

            informeService.exportarPDF(
                    tablaFCT.getItems(),
                    archivo
            );

            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION
            );

            alert.setTitle("Éxito");

            alert.setHeaderText(null);

            alert.setContentText(
                    "Informe generado correctamente"
            );

            alert.showAndWait();

        } catch (IOException e) {

            Alert alert = new Alert(
                    Alert.AlertType.ERROR
            );

            alert.setTitle("Error");

            alert.setHeaderText(null);

            alert.setContentText(
                    "No se pudo generar el informe PDF"
            );

            alert.showAndWait();

            e.printStackTrace();
        }
    }

    /**
     * Vuelve al menú del profesor.
     */
    @FXML
    private void volver() {

        stageManager.switchScene(
                FxmlView.PROFESOR
        );
    }
}