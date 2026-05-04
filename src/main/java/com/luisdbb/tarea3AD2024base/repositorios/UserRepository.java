package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.luisdbb.tarea3AD2024base.modelo.User;

/**
 * Repositorio encargado de gestionar el acceso a datos
 * de la entidad User.
 * 
 * Extiende JpaRepository para proporcionar operaciones CRUD
 * y define métodos personalizados para la búsqueda de usuarios.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su email.
     * 
     * Este método se utiliza principalmente para la autenticación.
     * 
     * @param email email del usuario
     * @return usuario encontrado o null si no existe
     */
    User findByEmail(String email);
}