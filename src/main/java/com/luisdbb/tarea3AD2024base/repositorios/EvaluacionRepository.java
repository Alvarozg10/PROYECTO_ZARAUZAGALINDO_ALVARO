package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Evaluacion;

@Repository
public interface EvaluacionRepository
        extends JpaRepository<Evaluacion, Long> {

}