package com.luisdbb.tarea3AD2024base.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.*;

@Controller
public class AnadirUsuarioController {

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private ChoiceBox<String> eleccionUsuario;
    @FXML private DatePicker dob;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    private ToggleGroup gender;

    @FXML
    public void initialize() {

        eleccionUsuario.getItems().addAll(
                "ADMIN",
                "PROFESOR",
                "ESTUDIANTE",
                "TUTOR_EMPRESA"
        );

        eleccionUsuario.setValue("ESTUDIANTE");

        gender = new ToggleGroup();
        rbMale.setToggleGroup(gender);
        rbFemale.setToggleGroup(gender);
        rbMale.setSelected(true);
    }

    @FXML
    private void saveUser() {

        if (firstName.getText().isEmpty() ||
            lastName.getText().isEmpty() ||
            email.getText().isEmpty() ||
            password.getText().isEmpty() ||
            dob.getValue() == null) {

            alerta("Error", "Rellena todos los campos");
            return;
        }

        User user = new User();
        user.setNombre(firstName.getText());
        user.setApellidos(lastName.getText());
        user.setEmail(email.getText());
        user.setPassword(password.getText());
        user.setFechaNacimiento(Date.valueOf(dob.getValue()));
        user.setGenero(rbMale.isSelected() ? "Male" : "Female");

        // 🔥 CLAVE
        user.setPerfil(eleccionUsuario.getValue());

        userService.save(user);

        alerta("OK", "Usuario creado correctamente");
        limpiar();
    }

    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.ADMIN);
    }

    private void limpiar() {
        firstName.clear();
        lastName.clear();
        email.clear();
        password.clear();
        dob.setValue(null);
        rbMale.setSelected(true);
        eleccionUsuario.setValue("ESTUDIANTE");
    }

    private void alerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}