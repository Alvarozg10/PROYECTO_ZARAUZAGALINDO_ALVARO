package com.luisdbb.tarea3AD2024base.repositorios;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisdbb.tarea3AD2024base.modelo.Asistencia;
import com.luisdbb.tarea3AD2024base.modelo.User;

public interface AsistenciaRepository
extends JpaRepository<Asistencia, Long> {

List<Asistencia> findByEstudiante(User estudiante);

Asistencia findByEstudianteAndFecha(
    User estudiante,
    Date fecha
);

long countByEstudiante(User estudiante);

long countByEstudianteAndAsistio(
    User estudiante,
    Boolean asistio
);
}