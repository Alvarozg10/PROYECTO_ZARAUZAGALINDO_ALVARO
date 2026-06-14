package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Asistencia;
import com.luisdbb.tarea3AD2024base.modelo.SeguimientoAlumnoDTO;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.AsistenciaRepository;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@Controller
public class SeguimientoFCTController
implements Initializable {

    @FXML
    private TableView<SeguimientoAlumnoDTO> tabla;

    @FXML
    private TableColumn<SeguimientoAlumnoDTO,String> colAlumno;

    @FXML
    private TableColumn<SeguimientoAlumnoDTO,Number> colHoras;

    @FXML
    private TableColumn<SeguimientoAlumnoDTO,Number> colAsistencias;

    @FXML
    private TableColumn<SeguimientoAlumnoDTO,Number> colFaltas;

    @Autowired
    private UserService userService;

    @Autowired
    private AsistenciaRepository asistenciaRepo;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Override
    public void initialize(
            URL location,
            ResourceBundle resources) {

        colAlumno.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getNombre()));

        colHoras.setCellValueFactory(data ->
                new SimpleLongProperty(
                        data.getValue().getHoras()));

        colAsistencias.setCellValueFactory(data ->
                new SimpleLongProperty(
                        data.getValue().getAsistencias()));

        colFaltas.setCellValueFactory(data ->
                new SimpleLongProperty(
                        data.getValue().getFaltas()));

        cargarDatos();
    }

    private void cargarDatos() {

        List<User> alumnos =
                userService.findAll()
                        .stream()
                        .filter(u ->
                                "ESTUDIANTE".equalsIgnoreCase(
                                        u.getPerfil()))
                        .toList();

        List<SeguimientoAlumnoDTO> datos =
                new ArrayList<>();

        for(User alumno : alumnos) {

            List<Asistencia> lista =
                    asistenciaRepo.findByEstudiante(
                            alumno);

            long horas =
                    lista.stream()
                            .mapToLong(a ->
                                    a.getHoras() != null
                                            ? a.getHoras()
                                            : 0)
                            .sum();

            long asistencias =
                    asistenciaRepo
                            .countByEstudianteAndAsistio(
                                    alumno,
                                    true);

            long faltas =
                    asistenciaRepo
                            .countByEstudianteAndAsistio(
                                    alumno,
                                    false);

            datos.add(
                    new SeguimientoAlumnoDTO(
                            alumno.getNombre()
                                    + " "
                                    + alumno.getApellidos(),
                            horas,
                            asistencias,
                            faltas
                    )
            );
        }

        tabla.setItems(
                FXCollections.observableArrayList(
                        datos));
    }

    @FXML
    private void volver() {
        stageManager.switchScene(
                FxmlView.DASHBOARD_PROFESOR);
    }
}