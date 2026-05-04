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

/**
 * Controlador encargado de gestionar la creación de nuevos usuarios.
 * 
 * Permite introducir los datos personales del usuario, validar la información
 * y guardarla en el sistema.
 */
@Controller
public class AnadirUsuarioController {

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField telefono;
    @FXML private PasswordField password;
    @FXML private ChoiceBox<String> eleccionUsuario;
    @FXML private DatePicker dob;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;

    /** Servicio encargado de la gestión de usuarios */
    @Autowired
    private UserService userService;

    /** Gestor de navegación entre vistas */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /** Grupo de selección para el género */
    private ToggleGroup gender;

    /**
     * Inicializa la vista configurando los valores por defecto
     * y las validaciones de los campos.
     */
    @FXML
    public void initialize() {

        eleccionUsuario.getItems().addAll(
                "PROFESOR",
                "ESTUDIANTE",
                "TUTOR_EMPRESA"
        );

        eleccionUsuario.setValue("ESTUDIANTE");

        gender = new ToggleGroup();
        rbMale.setToggleGroup(gender);
        rbFemale.setToggleGroup(gender);
        rbMale.setSelected(true);

        // Solo permite números en el campo teléfono
        telefono.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                telefono.setText(newVal.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * Valida los datos introducidos y guarda un nuevo usuario en el sistema.
     */
    @FXML
    private void saveUser() {

        if (firstName.getText().isEmpty() ||
            lastName.getText().isEmpty() ||
            email.getText().isEmpty() ||
            telefono.getText().isEmpty() ||
            password.getText().isEmpty() ||
            dob.getValue() == null) {

            alerta("Error", "Rellena todos los campos");
            return;
        }

        User user = new User();
        user.setNombre(firstName.getText());
        user.setApellidos(lastName.getText());
        user.setEmail(email.getText());
        user.setTelefono(telefono.getText());
        user.setPassword(password.getText());
        user.setFechaNacimiento(Date.valueOf(dob.getValue()));
        user.setGenero(rbMale.isSelected() ? "Male" : "Female");

        user.setPerfil(eleccionUsuario.getValue());

        userService.save(user);

        alerta("OK", "Usuario creado correctamente");
        limpiar();
    }

    /**
     * Vuelve al panel de administrador.
     */
    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.ADMIN);
    }

    /**
     * Limpia los campos del formulario tras crear un usuario.
     */
    private void limpiar() {
        firstName.clear();
        lastName.clear();
        email.clear();
        telefono.clear();
        password.clear();
        dob.setValue(null);
        rbMale.setSelected(true);
        eleccionUsuario.setValue("ESTUDIANTE");
    }

    /**
     * Muestra un mensaje emergente al usuario.
     * 
     * @param t título de la alerta
     * @param m mensaje a mostrar
     */
    private void alerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}