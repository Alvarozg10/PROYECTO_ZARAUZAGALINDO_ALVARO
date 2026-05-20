package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.luisdbb.tarea3AD2024base.modelo.User;

/**
 * Clase de pruebas unitarias para el modelo User.
 * 
 * Verifica el correcto funcionamiento
 * de getters y setters.
 */
public class UserModelTest {

    /**
     * Comprueba la creación correcta de un usuario.
     */
    @Test
    void crearUsuarioCorrectamente() {

        User user = new User();

        user.setNombre("Alvaro");
        user.setApellidos("Zarauza");

        assertEquals("Alvaro", user.getNombre());
        assertEquals("Zarauza", user.getApellidos());
    }

    /**
     * Comprueba que el género se almacena correctamente.
     */
    @Test
    void generoCorrecto() {

        User user = new User();

        user.setGenero("Hombre");

        assertEquals(
            "Hombre",
            user.getGenero()
        );
    }

    /**
     * Comprueba que la contraseña no es nula.
     */
    @Test
    void passwordNoDebeSerNull() {

        User user = new User();

        user.setPassword("1234");

        assertNotNull(user.getPassword());
    }
    
    @Test
    void nombreNoDebeSerNull() {

        User user = new User();

        user.setNombre("Alvaro");

        assertNotNull(user.getNombre());
    }

    @Test
    void apellidosCorrectos() {

        User user = new User();

        user.setApellidos("Zarauza");

        assertEquals(
            "Zarauza",
            user.getApellidos()
        );
    }

    @Test
    void emailCorrecto() {

        User user = new User();

        user.setEmail("test@gmail.com");

        assertTrue(
            user.getEmail().contains("@")
        );
    }

    @Test
    void telefonoLongitudCorrecta() {

        User user = new User();

        user.setTelefono("650954189");

        assertEquals(
            9,
            user.getTelefono().length()
        );
    }

    @Test
    void perfilCorrecto() {

        User user = new User();

        user.setPerfil("ESTUDIANTE");

        assertEquals(
            "ESTUDIANTE",
            user.getPerfil()
        );
    }
}