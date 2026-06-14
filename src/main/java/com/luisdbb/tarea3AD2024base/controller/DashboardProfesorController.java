package com.luisdbb.tarea3AD2024base.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estado;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.repositorios.EmpresaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;
import com.luisdbb.tarea3AD2024base.services.FCTService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Controller
public class DashboardProfesorController {

    @FXML
    private Label lblTotalFE;

    @FXML
    private Label lblEnCurso;

    @FXML
    private Label lblPendientes;

    @FXML
    private Label lblFinalizadas;

    @FXML
    private Label lblEmpresas;

    @FXML
    private Label lblEmpresaTop;

    @FXML
    private Label lblProximaFinalizacion;

    @FXML
    private Label lblFinalizanPronto;

    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    @Autowired
    private EmpresaRepository empresaRepo;

    @Autowired
    private FCTService fctService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    public void initialize() {

        fctService.actualizarEstadosAutomaticamente();

        List<FormacionEmpresa> lista =
                formacionRepo.findAll();

        LocalDate hoy = LocalDate.now();

        long total =
                lista.size();

        long enCurso =
                lista.stream()
                     .filter(fe ->
                             fe.getEstado() == Estado.EN_CURSO)
                     .count();

        long pendientes =
                lista.stream()
                     .filter(fe ->
                             fe.getEstado() == Estado.PENDIENTE)
                     .count();

        long finalizadas =
                lista.stream()
                     .filter(fe ->
                             fe.getEstado() == Estado.FINALIZADO)
                     .count();

        long empresas =
                empresaRepo.count();

        lblTotalFE.setText(
                String.valueOf(total));

        lblEnCurso.setText(
                String.valueOf(enCurso));

        lblPendientes.setText(
                String.valueOf(pendientes));

        lblFinalizadas.setText(
                String.valueOf(finalizadas));

        lblEmpresas.setText(
                String.valueOf(empresas));

        // EMPRESA CON MÁS ALUMNOS

        Map<String, Long> conteoEmpresas =
                lista.stream()
                     .filter(fe ->
                             fe.getEmpresa() != null)
                     .collect(Collectors.groupingBy(
                             fe -> fe.getEmpresa().getNombre(),
                             Collectors.counting()
                     ));

        String empresaTop =
                conteoEmpresas.entrySet()
                              .stream()
                              .max(Map.Entry.comparingByValue())
                              .map(e ->
                                      e.getKey()
                                      + " ("
                                      + e.getValue()
                                      + " alumnos)")
                              .orElse("Sin datos");

        lblEmpresaTop.setText(
                "Empresa líder: "
                + empresaTop);

        // PRÓXIMA FE EN FINALIZAR

        FormacionEmpresa proxima =
                lista.stream()
                     .filter(fe ->
                             fe.getFechaFin() != null
                             &&
                             fe.getEstado()
                             != Estado.FINALIZADO)
                     .min(Comparator.comparing(
                             FormacionEmpresa::getFechaFin))
                     .orElse(null);

        if (proxima != null) {

            lblProximaFinalizacion.setText(
                    "Próxima FE: "
                    + proxima.getEstudiante().getNombre()
                    + " "
                    + proxima.getEstudiante().getApellidos()
                    + " - "
                    + proxima.getFechaFin());

        } else {

            lblProximaFinalizacion.setText(
                    "No hay FE activas");
        }

        // FE QUE TERMINAN EN MENOS DE 15 DÍAS

        long terminanPronto =
                lista.stream()
                     .filter(fe -> {

                         if (fe.getFechaFin() == null) {
                             return false;
                         }

                         long dias =
                                 ChronoUnit.DAYS.between(
                                         hoy,
                                         fe.getFechaFin()
                                            .toLocalDate());

                         return dias >= 0
                                 && dias <= 15;
                     })
                     .count();

        lblFinalizanPronto.setText(
                "FE que terminan en 15 días: "
                + terminanPronto);
    }
    
    @FXML
    private void abrirSeguimiento() {

        stageManager.switchScene(
                FxmlView.SEGUIMIENTO_FCT
        );
    }

    @FXML
    private void volver() {

        stageManager.switchScene(
                FxmlView.PROFESOR
        );
    }
}