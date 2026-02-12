package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<Estudiante> findByTutorEmpresa_IdUsuario(Long tutorId);
}
