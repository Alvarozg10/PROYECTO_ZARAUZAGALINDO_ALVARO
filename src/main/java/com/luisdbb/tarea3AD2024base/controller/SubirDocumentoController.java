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

/**
 * Controlador encargado de gestionar la subida de documentos por parte del tutor de empresa.
 * 
 * Permite seleccionar un alumno asignado, elegir un archivo PDF desde el sistema
 * y almacenarlo en el servidor, asociándolo al estudiante correspondiente.
 */
@Controller
public class SubirDocumentoController {

    /** Selector de alumnos asignados al tutor */
    @FXML private ChoiceBox<User> cbAlumno;

    /** Etiqueta que muestra el nombre del archivo seleccionado */
    @FXML private Label lblArchivo;

    /** Archivo seleccionado para subir */
    private File archivoSeleccionado;

    /** Repositorio de documentos */
    @Autowired private DocumentoRepository documentoRepo;

    /** Repositorio de formaciones FCT */
    @Autowired private FormacionEmpresaRepository formacionRepo;

    /** Gestor de navegación entre vistas */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la vista cargando los alumnos asignados
     * al tutor logueado.
     * 
     * Filtra las FCT para obtener únicamente los estudiantes
     * asociados al tutor y evita duplicados.
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

        cbAlumno.setItems(FXCollections.observableArrayList(alumnos));

        // Conversor para mostrar el nombre del alumno
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

    /**
     * Abre un selector de archivos para elegir un documento PDF.
     */
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

    /**
     * Sube el documento seleccionado al sistema.
     * 
     * Copia el archivo a una carpeta local y guarda la información
     * en la base de datos asociándolo al alumno seleccionado.
     */
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

    /**
     * Vuelve al panel del tutor de empresa.
     */
    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.TUTOR_EMPRESA);
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