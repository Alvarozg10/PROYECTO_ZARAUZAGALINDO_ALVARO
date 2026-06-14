package com.luisdbb.tarea3AD2024base.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;

import org.springframework.web.bind.annotation.PathVariable;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;

import java.sql.Date;
import java.time.LocalDate;

import com.luisdbb.tarea3AD2024base.modelo.Asistencia;
import com.luisdbb.tarea3AD2024base.repositorios.AsistenciaRepository;

import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AsistenciaRepository asistenciaRepo;

    @GetMapping("/api/test")
    public Map<String, String> test() {

        Map<String, String> respuesta =
                new HashMap<>();

        respuesta.put(
                "estado",
                "API funcionando correctamente"
        );

        return respuesta;
    }

    @PostMapping("/api/login")
    public Object login(
            @RequestBody LoginRequest request) {

        User user =
                userService.authenticate(
                        request.getEmail(),
                        request.getPassword()
                );

        if (user == null) {

            Map<String, String> error =
                    new HashMap<>();

            error.put(
                    "error",
                    "Credenciales incorrectas"
            );

            return error;
        }

        return new LoginResponse(
                user.getIdUsuario(),
                user.getNombre(),
                user.getApellidos(),
                user.getPerfil(),
                user.getEmail()
        );
    }
    
    @GetMapping("/api/mifct/{idAlumno}")
    public Object miFct(
            @PathVariable Long idAlumno) {

        User alumno = userService.find(idAlumno);

        if (alumno == null) {

            Map<String, String> error =
                    new HashMap<>();

            error.put(
                    "error",
                    "Alumno no encontrado"
            );

            return error;
        }

        FormacionEmpresa fe =
                userService.findFormacionUnicaByEstudiante(
                        alumno
                );

        if (fe == null) {

            Map<String, String> error =
                    new HashMap<>();

            error.put(
                    "error",
                    "El alumno no tiene FE"
            );

            return error;
        }

        return new MiFCTResponse(

                fe.getEmpresa() != null
                        ? fe.getEmpresa().getNombre()
                        : "-",

                fe.getTutor() != null
                        ? fe.getTutor().getNombre()
                        + " "
                        + fe.getTutor().getApellidos()
                        : "-",

                fe.getEstado() != null
                        ? fe.getEstado().toString()
                        : "-",

                fe.getFechaInicio() != null
                        ? fe.getFechaInicio().toString()
                        : "-",

                fe.getFechaFin() != null
                        ? fe.getFechaFin().toString()
                        : "-"
        );
    }
    
    @PostMapping("/api/asistencia")
    public Object registrarAsistencia(
            @RequestBody AsistenciaRequest request) {

        User alumno =
                userService.find(
                        request.getIdAlumno()
                );

        if (alumno == null) {

            Map<String, String> error =
                    new HashMap<>();

            error.put(
                    "error",
                    "Alumno no encontrado"
            );

            return error;
        }

        Date hoy =
                Date.valueOf(
                        LocalDate.now()
                );

        Asistencia existente =
                asistenciaRepo.findByEstudianteAndFecha(
                        alumno,
                        hoy
                );

        if (existente != null) {

            Map<String, String> error =
                    new HashMap<>();

            error.put(
                    "error",
                    "La asistencia de hoy ya fue registrada"
            );

            return error;
        }

        Asistencia asistencia =
                new Asistencia();

        asistencia.setEstudiante(
                alumno
        );

        asistencia.setFecha(
                hoy
        );

        asistencia.setAsistio(
                request.getAsistio()
        );

        if (Boolean.TRUE.equals(
                request.getAsistio())) {

            asistencia.setHoras(8);

        } else {

            asistencia.setHoras(0);

            asistencia.setMotivo(
                    request.getMotivo()
            );

            asistencia.setComentario(
                    request.getComentario()
            );

            asistencia.setJustificante(
                    request.getJustificante()
            );
        }

        asistenciaRepo.save(
                asistencia
        );

        Map<String, String> respuesta =
                new HashMap<>();

        respuesta.put(
                "estado",
                "Asistencia registrada correctamente"
        );

        return respuesta;
    }
    
    @GetMapping("/api/dashboard/{idAlumno}")
    public Object dashboard(
            @PathVariable Long idAlumno) {

        User alumno =
                userService.find(idAlumno);

        if (alumno == null) {

            Map<String, String> error =
                    new HashMap<>();

            error.put(
                    "error",
                    "Alumno no encontrado"
            );

            return error;
        }

        FormacionEmpresa fe =
                userService.findFormacionUnicaByEstudiante(
                        alumno
                );

        if (fe == null) {

            Map<String, String> error =
                    new HashMap<>();

            error.put(
                    "error",
                    "El alumno no tiene FE"
            );

            return error;
        }

        List<Asistencia> asistencias =
                asistenciaRepo.findByEstudiante(
                        alumno
                );

        int horasCotizadas =

                asistencias.stream()
                        .mapToInt(a ->
                                a.getHoras() != null
                                        ? a.getHoras()
                                        : 0)
                        .sum();

        int faltas =

                (int) asistenciaRepo
                        .countByEstudianteAndAsistio(
                                alumno,
                                false
                        );

        int diasAsistidos =

                (int) asistenciaRepo
                        .countByEstudianteAndAsistio(
                                alumno,
                                true
                        );

        long diasTotales =

                ChronoUnit.DAYS.between(

                        fe.getFechaInicio()
                                .toLocalDate(),

                        fe.getFechaFin()
                                .toLocalDate()

                ) + 1;

        int diasRegistrados =
                asistencias.size();

        long diasRestantes =
                diasTotales - diasRegistrados;

        if (diasRestantes < 0) {
            diasRestantes = 0;
        }

        int porcentaje =

                (int) ((diasRegistrados * 100.0)
                        / diasTotales);

        return new DashboardResponse(

                horasCotizadas,

                faltas,

                diasAsistidos,

                (int) diasTotales,

                diasRestantes,

                porcentaje
        );
    }
    
    @GetMapping("/api/calendario/{idAlumno}")
    public Object calendario(
            @PathVariable Long idAlumno) {

        User alumno =
                userService.find(idAlumno);

        if (alumno == null) {

            Map<String, String> error =
                    new HashMap<>();

            error.put(
                    "error",
                    "Alumno no encontrado"
            );

            return error;
        }

        List<Asistencia> asistencias =
                asistenciaRepo.findByEstudiante(
                        alumno
                );

        return asistencias.stream()

                .map(a ->

                        new CalendarioResponse(

                                a.getFecha().toString(),

                                a.getAsistio()

                        )
                )

                .toList();
    }
    
    @GetMapping("/api/ausencias/{idAlumno}")
    public Object ausencias(
            @PathVariable Long idAlumno) {

        User alumno =
                userService.find(idAlumno);

        if (alumno == null) {

            Map<String, String> error =
                    new HashMap<>();

            error.put(
                    "error",
                    "Alumno no encontrado"
            );

            return error;
        }

        List<Asistencia> asistencias =
                asistenciaRepo.findByEstudiante(
                        alumno
                );

        return asistencias.stream()

                .filter(a ->
                        Boolean.FALSE.equals(
                                a.getAsistio()
                        )
                )

                .map(a ->

                        new AusenciaResponse(

                                a.getFecha() != null
                                        ? a.getFecha().toString()
                                        : "",

                                a.getMotivo() != null
                                        ? a.getMotivo()
                                        : "Sin motivo",

                                a.getComentario() != null
                                        ? a.getComentario()
                                        : ""
                        )
                )

                .toList();
    }
}