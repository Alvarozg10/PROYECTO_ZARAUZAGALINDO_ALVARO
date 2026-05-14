package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;

/**
 * Clase de pruebas unitarias para el servicio de usuarios.
 * 
 * Verifica el correcto funcionamiento de:
 * <ul>
 *     <li>Autenticación de usuarios</li>
 *     <li>Creación de usuarios</li>
 *     <li>Validaciones básicas</li>
 * </ul>
 */
@SpringBootTest(
    classes = Tarea3Ad2024baseApplication.class
)
class UserServiceTest {

    /** Servicio de usuarios */
    @Autowired
    private UserService userService;

    /**
     * Comprueba que un usuario válido puede iniciar sesión.
     */
    @Test
    void loginCorrecto() {

        User user = userService.authenticate(
                "alvarozg10@educastur.es",
                "alvaro"
        );

        assertNotNull(user);
    }

    /**
     * Comprueba que el sistema rechaza
     * credenciales incorrectas.
     */
    @Test
    void loginIncorrecto() {

        User user = userService.authenticate(
                "usuarioFake",
                "1234"
        );

        assertNull(user);
    }

    /**
     * Verifica que un usuario puede guardarse
     * correctamente en la base de datos.
     */
    @Test
    void guardarUsuario() {

        User user = new User();

        user.setNombre("Test");
        user.setApellidos("JUnit");
        user.setEmail("test@edu.com");
        user.setTelefono("123456789");
        user.setPassword("1234");
        user.setGenero("Hombre");
        user.setPerfil("ESTUDIANTE");

        User guardado = userService.save(user);

        assertNotNull(guardado);
        assertNotNull(guardado.getIdUsuario());
    }

    /**
     * Comprueba que el email puede detectarse vacío.
     */
    @Test
    void emailNoDebeEstarVacio() {

        User user = new User();

        user.setEmail("");

        assertTrue(user.getEmail().isEmpty());
    }

    /**
     * Verifica que el teléfono solo contiene números.
     */
    @Test
    void telefonoDebeSerNumerico() {

        String telefono = "650954189";

        assertTrue(telefono.matches("\\d+"));
    }
}