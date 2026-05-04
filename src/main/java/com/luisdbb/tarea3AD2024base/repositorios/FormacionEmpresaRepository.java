package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.User;

/**
 * Repositorio encargado de gestionar el acceso a datos
 * de la entidad FormacionEmpresa.
 * 
 * Proporciona operaciones CRUD básicas mediante JpaRepository
 * y consultas personalizadas basadas en el tutor y el estudiante.
 */
public interface FormacionEmpresaRepository extends JpaRepository<FormacionEmpresa, Long> {

    /**
     * Obtiene las formaciones en empresa asociadas a un tutor.
     * 
     * @param tutor usuario con rol de tutor
     * @return lista de formaciones del tutor
     */
    List<FormacionEmpresa> findByTutor(User tutor);

    /**
     * Obtiene las formaciones en empresa asociadas a un estudiante.
     * 
     * @param estudiante usuario con rol de estudiante
     * @return lista de formaciones del estudiante
     */
    List<FormacionEmpresa> findByEstudiante(User estudiante);
}