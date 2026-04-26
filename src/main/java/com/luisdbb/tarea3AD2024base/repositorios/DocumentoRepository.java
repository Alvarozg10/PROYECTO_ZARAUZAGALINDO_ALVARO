package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisdbb.tarea3AD2024base.modelo.Documento;
import com.luisdbb.tarea3AD2024base.modelo.User;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findByEstudiante(User estudiante);
}