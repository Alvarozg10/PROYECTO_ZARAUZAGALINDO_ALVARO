package com.luisdbb.tarea3AD2024base.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Empresa;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;
import com.luisdbb.tarea3AD2024base.services.FCTService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

@Controller
public class DatosFCTAlumnoController {

    @FXML private Label lblEmpresa;
    @FXML private Label lblDireccion;
    @FXML private Label lblTelefonoEmpresa;
    @FXML private Label lblCorreoEmpresa;

    @FXML private Label lblTutor;
    @FXML private Label lblCorreoTutor;
    @FXML private Label lblTelefonoTutor;

    @FXML private Label lblInicio;
    @FXML private Label lblFin;
    @FXML private Label lblEstado;

    @FXML private Label lblPorcentaje;
    @FXML private Label lblDiasRealizados;
    @FXML private Label lblDiasRestantes;
    @FXML private Label lblDuracionTotal;

    @FXML private ProgressBar progressFE;

    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    @Autowired
    private FCTService fctService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    public void initialize() {

        fctService.actualizarEstadosAutomaticamente();

        User alumno = stageManager.getLoggedUser();

        FormacionEmpresa f = formacionRepo.findAll()
                .stream()
                .filter(x ->
                        x.getEstudiante() != null
                        &&
                        x.getEstudiante()
                         .getIdUsuario()
                         .equals(alumno.getIdUsuario()))
                .findFirst()
                .orElse(null);

        if (f == null) {

            lblEmpresa.setText("-");
            lblDireccion.setText("-");
            lblTelefonoEmpresa.setText("-");
            lblCorreoEmpresa.setText("-");

            lblTutor.setText("-");
            lblCorreoTutor.setText("-");
            lblTelefonoTutor.setText("-");

            lblInicio.setText("-");
            lblFin.setText("-");
            lblEstado.setText("-");

            lblPorcentaje.setText("0%");
            lblDiasRealizados.setText("0");
            lblDiasRestantes.setText("0");
            lblDuracionTotal.setText("0");

            progressFE.setProgress(0);

            return;
        }

        Empresa empresa = f.getEmpresa();

        if (empresa != null) {

            lblEmpresa.setText(empresa.getNombre());
            lblDireccion.setText(empresa.getDireccion());
            lblTelefonoEmpresa.setText(empresa.getTelefono());
            lblCorreoEmpresa.setText(empresa.getCorreoElectronico());
        }

        if (f.getTutor() != null) {

            lblTutor.setText(
                    f.getTutor().getNombre()
                    + " "
                    + f.getTutor().getApellidos()
            );

            lblCorreoTutor.setText(
                    f.getTutor().getEmail()
            );

            lblTelefonoTutor.setText(
                    f.getTutor().getTelefono()
            );
        }

        lblInicio.setText(
                f.getFechaInicio().toString()
        );

        lblFin.setText(
                f.getFechaFin().toString()
        );

        lblEstado.setText(
                f.getEstado().toString()
        );

        LocalDate hoy = LocalDate.now();

        LocalDate inicio =
                f.getFechaInicio().toLocalDate();

        LocalDate fin =
                f.getFechaFin().toLocalDate();

        long duracionTotal =
                ChronoUnit.DAYS.between(
                        inicio,
                        fin
                );

        long realizados =
                ChronoUnit.DAYS.between(
                        inicio,
                        hoy
                );

        if (realizados < 0) {
            realizados = 0;
        }

        if (realizados > duracionTotal) {
            realizados = duracionTotal;
        }

        long restantes =
                duracionTotal - realizados;

        double porcentaje =
                duracionTotal > 0
                ? (double) realizados / duracionTotal
                : 0;

        progressFE.setProgress(
                porcentaje
        );

        lblPorcentaje.setText(
                (int)(porcentaje * 100)
                + "%"
        );

        lblDiasRealizados.setText(
                String.valueOf(realizados)
        );

        lblDiasRestantes.setText(
                String.valueOf(restantes)
        );

        lblDuracionTotal.setText(
                String.valueOf(duracionTotal)
        );
    }

    @FXML
    private void volver() {

        stageManager.switchScene(
                FxmlView.ESTUDIANTE
        );
    }
}