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

@Controller
public class DatosFCTAlumnoController {

    @FXML private Label lblEmpresa;
    @FXML private Label lblTutor;
    @FXML private Label lblEmailTutor;
    @FXML private Label lblTelefonoTutor;

    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    @Lazy
    @Autowired
    private StageManager stageManager;

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

    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.ESTUDIANTE);
    }
}