package tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;

@SpringBootTest
public class UserServiceTest {



	    @Autowired
	    private UserService userService;

	    @Test
	    void loginCorrecto() {

	        User user = userService.authenticate(
	                "admin",
	                "admin"
	        );

	        assertNotNull(user);
	    }

	    @Test
	    void loginIncorrecto() {

	        User user = userService.authenticate(
	                "pepe",
	                "1234"
	        );

	        assertNull(user);
	    }
	}
