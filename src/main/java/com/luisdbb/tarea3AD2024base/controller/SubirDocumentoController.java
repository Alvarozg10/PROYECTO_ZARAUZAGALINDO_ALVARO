package com.luisdbb.tarea3AD2024base.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

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
import javafx.scene.control.*;
import javafx.stage.FileChooser;

@Controller
public class SubirDocumentoController {

    @FXML private ChoiceBox<User> cbAlumno;
    @FXML private Label lblArchivo;

    private File archivoSeleccionado;

    @Autowired private DocumentoRepository documentoRepo;
    @Autowired private FormacionEmpresaRepository formacionRepo;

    @Lazy
    @Autowired
    private StageManager stageManager;

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

        cbAlumno.setItems(FXCollections.observableArrayList(alumnos));

        cbAlumno.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(User u) {
                return u != null ? u.getNombre() : "";
            }

            @Override
            public User fromString(String s) {
                return null;
            }
        });
    }

    @FXML
    private void seleccionarArchivo() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );

        archivoSeleccionado = fileChooser.showOpenDialog(null);

        if (archivoSeleccionado != null) {
            lblArchivo.setText(archivoSeleccionado.getName());
        }
    }

    @FXML
    private void subirDocumento() {

        if (archivoSeleccionado == null || cbAlumno.getValue() == null) {
            alerta("Error", "Selecciona alumno y archivo");
            return;
        }

        try {
            String carpeta = "documentos/";
            new File(carpeta).mkdirs();

            File destino = new File(carpeta + archivoSeleccionado.getName());

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

        } catch (Exception e) {
            e.printStackTrace();
            alerta("Error", "No se pudo subir el documento");
        }
    }

    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.TUTOR_EMPRESA);
    }

    private void alerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}