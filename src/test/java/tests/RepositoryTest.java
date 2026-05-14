package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.UserRepository;

/**
 * Clase de pruebas unitarias para el repositorio de usuarios.
 * 
 * Verifica consultas y acceso a datos.
 */
@SpringBootTest(
    classes = Tarea3Ad2024baseApplication.class
)
class RepositoryTest {

    /** Repositorio de usuarios */
    @Autowired
    private UserRepository userRepository;

    /**
     * Comprueba que se pueden recuperar usuarios.
     */
    @Test
    void buscarUsuarios() {

        List<User> users = userRepository.findAll();

        assertNotNull(users);
    }

    /**
     * Comprueba que existen usuarios en la base de datos.
     */
    @Test
    void existeUsuarios() {

        List<User> users = userRepository.findAll();

        assertFalse(users.isEmpty());
    }
}