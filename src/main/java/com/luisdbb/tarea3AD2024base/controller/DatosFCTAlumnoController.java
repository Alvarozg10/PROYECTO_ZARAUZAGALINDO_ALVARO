package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controlador encargado de mostrar la información de la FCT
 * asignada al alumno logueado.
 * 
 * Permite visualizar los datos de la empresa, el tutor asignado
 * y su información de contacto.
 */
@Controller
public class DatosFCTAlumnoController {

    /** Etiqueta que muestra la empresa asignada */
    @FXML private Label lblEmpresa;

    /** Etiqueta que muestra el nombre del tutor */
    @FXML private Label lblTutor;

    /** Etiqueta que muestra el email del tutor */
    @FXML private Label lblEmailTutor;

    /** Etiqueta que muestra el teléfono del tutor */
    @FXML private Label lblTelefonoTutor;

    /** Repositorio de formaciones FCT */
    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    /** Gestor de navegación entre vistas */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la vista cargando la información de la FCT
     * correspondiente al alumno logueado.
     * 
     * Busca la formación asociada al estudiante y muestra
     * los datos de la empresa y del tutor.
     */
    @FXML
    public void initialize() {

        User alumno = stageManager.getLoggedUser();

        FormacionEmpresa f = formacionRepo.findAll()
                .stream()
                .filter(x -> x.getEstudiante() != null &&
                             x.getEstudiante().getIdUsuario().equals(alumno.getIdUsuario()))
                .findFirst()
                .orElse(null);

        if (f == null) {
            lblEmpresa.setText("Empresa: No asignada");
            lblTutor.setText("Tutor: No asignado");
            return;
        }

        lblEmpresa.setText("Empresa: " + f.getEmpresa());

        User tutor = f.getTutor();

        if (tutor != null) {
            lblTutor.setText("Tutor: " + tutor.getNombre() + " " + tutor.getApellidos());
            lblEmailTutor.setText("Email: " + tutor.getEmail());
            lblTelefonoTutor.setText("Teléfono: " +
                (tutor.getTelefono() != null ? tutor.getTelefono() : "-"));
        } else {
            lblTutor.setText("Tutor: No asignado");
        }
    }

    /**
     * Vuelve al panel principal del estudiante.
     */
    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.ESTUDIANTE);
    }
}