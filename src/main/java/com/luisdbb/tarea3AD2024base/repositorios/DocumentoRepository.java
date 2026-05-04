package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisdbb.tarea3AD2024base.modelo.Documento;
import com.luisdbb.tarea3AD2024base.modelo.User;

/**
 * Repositorio encargado de gestionar el acceso a datos
 * de la entidad Documento.
 * 
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define métodos personalizados de consulta.
 */
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    /**
     * Obtiene todos los documentos asociados a un estudiante.
     * 
     * @param estudiante usuario estudiante
     * @return lista de documentos del estudiante
     */
    List<Documento> findByEstudiante(User estudiante);
}