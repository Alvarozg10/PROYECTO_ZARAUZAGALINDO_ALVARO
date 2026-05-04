package com.luisdbb.tarea3AD2024base.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Documento;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.DocumentoRepository;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

/**
 * Controlador encargado de gestionar los documentos del alumno.
 * 
 * Permite visualizar los documentos asociados al estudiante logueado
 * y descargarlos al sistema local.
 */
@Controller
public class DocumentosAlumnoController implements Initializable {

    /** Tabla que muestra los documentos del alumno */
    @FXML private TableView<Documento> tablaDocs;

    /** Columna que muestra el nombre del documento */
    @FXML private TableColumn<Documento, String> colNombre;

    /** Repositorio de documentos */
    @Autowired
    private DocumentoRepository documentoRepo;

    /** Gestor de navegación entre vistas */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la tabla configurando las columnas
     * y cargando los documentos del alumno.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colNombre.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getNombre())
        );

        tablaDocs.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cargarDatos();
    }

    /**
     * Carga los documentos asociados al alumno logueado
     * desde la base de datos.
     */
    private void cargarDatos() {

        User alumno = stageManager.getLoggedUser();

        List<Documento> docs = documentoRepo.findByEstudiante(alumno);

        tablaDocs.setItems(FXCollections.observableArrayList(docs));
    }

    /**
     * Permite descargar el documento seleccionado
     * al sistema local del usuario.
     * 
     * Abre un selector de archivos para elegir la ubicación
     * y copia el archivo desde su ruta original.
     */
    @FXML
    private void descargarDocumento() {

        Documento doc = tablaDocs.getSelectionModel().getSelectedItem();

        if (doc == null) {
            alerta("Error", "Selecciona un documento");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(doc.getNombre());

        File destino = fileChooser.showSaveDialog(null);

        if (destino != null) {
            try {
                Files.copy(
                    new File(doc.getRuta()).toPath(),
                    destino.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                );

                alerta("OK", "Documento descargado correctamente");

            } catch (Exception e) {
                e.printStackTrace();
                alerta("Error", "No se pudo descargar");
            }
        }
    }

    /**
     * Vuelve al panel principal del estudiante.
     */
    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.ESTUDIANTE);
    }

    /**
     * Muestra una alerta informativa al usuario.
     * 
     * @param t título de la alerta
     * @param m mensaje a mostrar
     */
    private void alerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}