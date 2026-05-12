package com.luisdbb.tarea3AD2024base.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Documento;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.DocumentoRepository;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controlador encargado de gestionar la subida de documentos.
 */
@Controller
public class SubirDocumentoController {

    @FXML private ChoiceBox<User> cbAlumno;
    @FXML private Label lblArchivo;

    /** Archivo seleccionado */
    private File archivoSeleccionado;

    @Autowired
    private DocumentoRepository documentoRepo;

    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la vista.
     */
    @FXML
    public void initialize() {

        User tutor = stageManager.getLoggedUser();

        List<User> alumnos = formacionRepo.findAll()
                .stream()
                .filter(f -> f.getTutor() != null &&
                             f.getTutor().getIdUsuario().equals(tutor.getIdUsuario()))
                .map(FormacionEmpresa::getEstudiante)
                .distinct()
                .toList();

        cbAlumno.setItems(
            FXCollections.observableArrayList(alumnos)
        );

        cbAlumno.setConverter(new javafx.util.StringConverter<>() {

            @Override
            public String toString(User u) {
                return u != null
                        ? u.getNombre() + " " + u.getApellidos()
                        : "";
            }

            @Override
            public User fromString(String s) {
                return null;
            }
        });
    }

    /**
     * Selecciona un archivo PDF.
     */
    @FXML
    private void seleccionarArchivo() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Seleccionar PDF");

        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter(
                "Archivos PDF",
                "*.pdf"
            )
        );

        Stage stage = (Stage) cbAlumno.getScene().getWindow();

        archivoSeleccionado = fileChooser.showOpenDialog(stage);

        if (archivoSeleccionado != null) {
            lblArchivo.setText(archivoSeleccionado.getName());
        }
    }

    /**
     * Sube el documento.
     */
    @FXML
    private void subirDocumento() {

        if (archivoSeleccionado == null ||
            cbAlumno.getValue() == null) {

            alerta("Error", "Selecciona alumno y archivo");
            return;
        }

        try {

            if (!archivoSeleccionado.exists()) {
                alerta("Error", "El archivo no existe");
                return;
            }

            String carpeta = "documentos/";

            File directorio = new File(carpeta);

            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            // Nombre único para evitar sobrescribir
            String nombreUnico =
                    UUID.randomUUID() + "_"
                    + archivoSeleccionado.getName();

            File destino = new File(carpeta + nombreUnico);

            Files.copy(
                    archivoSeleccionado.toPath(),
                    destino.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

            Documento doc = new Documento();

            doc.setNombre(archivoSeleccionado.getName());
            doc.setRuta(destino.getAbsolutePath());
            doc.setEstudiante(cbAlumno.getValue());

            documentoRepo.save(doc);

            alerta("OK", "Documento subido correctamente");

            limpiar();

        } catch (Exception e) {

            e.printStackTrace();

            alerta(
                "Error",
                "No se pudo subir el documento"
            );
        }
    }

    /**
     * Limpia el formulario.
     */
    private void limpiar() {

        archivoSeleccionado = null;

        lblArchivo.setText("Ningún archivo seleccionado");

        cbAlumno.setValue(null);
    }

    /**
     * Vuelve al panel principal.
     */
    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.TUTOR_EMPRESA);
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