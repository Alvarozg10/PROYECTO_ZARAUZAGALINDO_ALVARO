package com.luisdbb.tarea3AD2024base.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controlador encargado de gestionar los documentos del alumno.
 */
@Controller
public class DocumentosAlumnoController implements Initializable {

    @FXML private TableView<Documento> tablaDocs;
    @FXML private TableColumn<Documento, String> colNombre;

    @Autowired
    private DocumentoRepository documentoRepo;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colNombre.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getNombre())
        );

        tablaDocs.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cargarDatos();
    }

    /**
     * Carga los documentos del alumno logueado.
     */
    private void cargarDatos() {

        User alumno = stageManager.getLoggedUser();

        List<Documento> docs = documentoRepo.findByEstudiante(alumno);

        tablaDocs.setItems(
            FXCollections.observableArrayList(docs)
        );
    }

    /**
     * Descarga el documento seleccionado.
     */
    @FXML
    private void descargarDocumento() {

        Documento doc = tablaDocs.getSelectionModel().getSelectedItem();

        if (doc == null) {
            alerta("Error", "Selecciona un documento");
            return;
        }

        try {

            File archivoOriginal = new File(doc.getRuta());

            if (!archivoOriginal.exists()) {
                alerta("Error", "El archivo original no existe");
                return;
            }

            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Guardar documento");
            fileChooser.setInitialFileName(doc.getNombre());

            Stage stage = (Stage) tablaDocs.getScene().getWindow();

            File destino = fileChooser.showSaveDialog(stage);

            if (destino != null) {

                Files.copy(
                    archivoOriginal.toPath(),
                    destino.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                );

                alerta("OK", "Documento descargado correctamente");
            }

        } catch (Exception e) {
            e.printStackTrace();
            alerta("Error", "No se pudo descargar el documento");
        }
    }

    /**
     * Vuelve al panel del estudiante.
     */
    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.ESTUDIANTE);
    }

    /**
     * Muestra una alerta.
     */
    private void alerta(String t, String m) {

        Alert a = new Alert(Alert.AlertType.INFORMATION);

        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);

        a.showAndWait();
    }
}