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
 * Controlador encargado de mostrar la información
 * de la FCT del alumno logueado.
 */
@Controller
public class DatosFCTAlumnoController {

    /** Empresa */
    @FXML
    private Label lblEmpresa;

    /** Tutor */
    @FXML
    private Label lblTutor;

    /** Email tutor */
    @FXML
    private Label lblEmailTutor;

    /** Teléfono tutor */
    @FXML
    private Label lblTelefonoTutor;

    /** Curso y ciclo */
    @FXML
    private Label lblCursoCiclo;

    /** Estado FCT */
    @FXML
    private Label lblEstado;

    /** Fechas */
    @FXML
    private Label lblFechas;

    /** Repositorio */
    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    /** Navegación */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la vista.
     */
    @FXML
    public void initialize() {

        User alumno = stageManager.getLoggedUser();

        FormacionEmpresa f = formacionRepo.findAll()
                .stream()
                .filter(x ->
                        x.getEstudiante() != null &&
                        x.getEstudiante()
                         .getIdUsuario()
                         .equals(alumno.getIdUsuario()))
                .findFirst()
                .orElse(null);

        // Sin FCT
        if (f == null) {

            lblEmpresa.setText("Empresa: No asignada");
            lblTutor.setText("Tutor: No asignado");

            lblEmailTutor.setText("Email: -");
            lblTelefonoTutor.setText("Teléfono: -");

            lblCursoCiclo.setText(
                    "Curso: "
                    + alumno.getCurso()
                    + " - "
                    + alumno.getCiclo()
            );

            lblEstado.setText("Estado: -");
            lblFechas.setText("Fechas: -");

            return;
        }

        // Empresa
        lblEmpresa.setText(
                "Empresa: " + f.getEmpresa()
        );

        // Curso y ciclo
        lblCursoCiclo.setText(
                "Curso: "
                + alumno.getCurso()
                + " - "
                + alumno.getCiclo()
        );

        // Estado
        lblEstado.setText(
                "Estado: " + f.getEstado()
        );

        // Fechas
        lblFechas.setText(
                "Duración: "
                + f.getFechaInicio()
                + " → "
                + f.getFechaFin()
        );

        // Tutor
        User tutor = f.getTutor();

        if (tutor != null) {

            lblTutor.setText(
                    "Tutor: "
                    + tutor.getNombre()
                    + " "
                    + tutor.getApellidos()
            );

            lblEmailTutor.setText(
                    "Email: " + tutor.getEmail()
            );

            lblTelefonoTutor.setText(
                    "Teléfono: "
                    + (tutor.getTelefono() != null
                            ? tutor.getTelefono()
                            : "-")
            );

        } else {

            lblTutor.setText("Tutor: No asignado");

            lblEmailTutor.setText("Email: -");
            lblTelefonoTutor.setText("Teléfono: -");
        }
    }

    /**
     * Vuelve al panel del estudiante.
     */
    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.ESTUDIANTE);
    }
}