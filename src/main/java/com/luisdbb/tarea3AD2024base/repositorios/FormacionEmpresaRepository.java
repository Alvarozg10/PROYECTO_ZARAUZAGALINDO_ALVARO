package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.User;

public interface FormacionEmpresaRepository extends JpaRepository<FormacionEmpresa, Long> {

    List<FormacionEmpresa> findByTutor(User tutor);

    List<FormacionEmpresa> findByEstudiante(User estudiante);
}