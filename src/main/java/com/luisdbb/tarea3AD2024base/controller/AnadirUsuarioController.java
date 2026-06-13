package com.luisdbb.tarea3AD2024base.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Ciclo;
import com.luisdbb.tarea3AD2024base.modelo.Curso;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

@Controller
public class AnadirUsuarioController {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private TextField telefono;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<String> eleccionUsuario;

    @FXML
    private DatePicker dob;

    @FXML
    private RadioButton rbMale;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private ComboBox<Curso> cbCurso;

    @FXML
    private ComboBox<Ciclo> cbCiclo;

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    private ToggleGroup gender;

    @FXML
    public void initialize() {

        eleccionUsuario.getItems().addAll(
                "PROFESOR",
                "ESTUDIANTE",
                "TUTOR_EMPRESA"
        );

        gender = new ToggleGroup();

        rbMale.setToggleGroup(gender);
        rbFemale.setToggleGroup(gender);

        rbMale.setSelected(true);

        telefono.textProperty().addListener((obs, oldVal, newVal) -> {

            if (!newVal.matches("\\d*")) {

                telefono.setText(
                        newVal.replaceAll("[^\\d]", "")
                );
            }
        });

        cbCurso.getItems().addAll(
                Curso.values()
        );

        cbCiclo.getItems().addAll(
                Ciclo.values()
        );

        eleccionUsuario.valueProperty().addListener(
                (obs, oldVal, newVal) -> {

                    boolean esTutor =
                            "TUTOR_EMPRESA"
                                    .equalsIgnoreCase(newVal);

                    cbCurso.setVisible(!esTutor);
                    cbCurso.setManaged(!esTutor);

                    cbCiclo.setVisible(!esTutor);
                    cbCiclo.setManaged(!esTutor);

                    if (esTutor) {

                        cbCurso.setValue(null);
                        cbCiclo.setValue(null);
                    }
                });

        eleccionUsuario.setValue(
                "ESTUDIANTE"
        );
    }

    @FXML
    private void saveUser() {

        boolean esTutor =
                "TUTOR_EMPRESA"
                        .equalsIgnoreCase(
                                eleccionUsuario.getValue()
                        );

        if (firstName.getText().isEmpty()
                || lastName.getText().isEmpty()
                || email.getText().isEmpty()
                || telefono.getText().isEmpty()
                || password.getText().isEmpty()
                || dob.getValue() == null
                || (!esTutor && cbCurso.getValue() == null)
                || (!esTutor && cbCiclo.getValue() == null)) {

            alerta(
                    "Error",
                    "Rellena todos los campos"
            );

            return;
        }

        User user = new User();

        user.setNombre(
                firstName.getText()
        );

        user.setApellidos(
                lastName.getText()
        );

        user.setEmail(
                email.getText()
        );

        user.setTelefono(
                telefono.getText()
        );

        user.setPassword(
                password.getText()
        );

        user.setFechaNacimiento(
                Date.valueOf(
                        dob.getValue()
                )
        );

        user.setGenero(
                rbMale.isSelected()
                        ? "Hombre"
                        : "Mujer"
        );

        user.setPerfil(
                eleccionUsuario.getValue()
        );

        if (!esTutor) {

            user.setCurso(
                    cbCurso.getValue()
            );

            user.setCiclo(
                    cbCiclo.getValue()
            );
        }

        userService.save(user);

        alerta(
                "OK",
                "Usuario creado correctamente"
        );

        limpiar();
    }

    @FXML
    private void volver() {

        stageManager.switchScene(
                FxmlView.ADMIN
        );
    }

    private void limpiar() {

        firstName.clear();
        lastName.clear();
        email.clear();
        telefono.clear();
        password.clear();

        dob.setValue(null);

        rbMale.setSelected(true);

        eleccionUsuario.setValue(
                "ESTUDIANTE"
        );

        cbCurso.setValue(null);
        cbCiclo.setValue(null);

        cbCurso.setVisible(true);
        cbCurso.setManaged(true);

        cbCiclo.setVisible(true);
        cbCiclo.setManaged(true);
    }

    private void alerta(
            String titulo,
            String mensaje) {

        Alert a = new Alert(
                Alert.AlertType.INFORMATION
        );

        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);

        a.showAndWait();
    }
}