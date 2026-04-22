package com.luisdbb.tarea3AD2024base.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class ModificarUsuarioController {

    @FXML private TableView<User> tablaUsuarios;
    @FXML private TableColumn<User, String> colNombre;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colPerfil;

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField telefono;
    @FXML private PasswordField password;
    @FXML private ChoiceBox<String> eleccionUsuario;
    @FXML private DatePicker dob;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;

    private ToggleGroup gender;
    private User usuario;

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    public void initialize() {

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPerfil.setCellValueFactory(new PropertyValueFactory<>("perfil"));

        tablaUsuarios.setItems(
            FXCollections.observableArrayList(userService.findAll())
        );

        eleccionUsuario.getItems().addAll(
                "PROFESOR", "ESTUDIANTE", "TUTOR_EMPRESA"
        );

        gender = new ToggleGroup();
        rbMale.setToggleGroup(gender);
        rbFemale.setToggleGroup(gender);

        tablaUsuarios.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    usuario = row.getItem();
                    cargarDatos(usuario);
                }
            });
            return row;
        });
    }

    @FXML
    private void cargarUsuario() {

        usuario = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuario == null) {
            alerta("Error", "Selecciona un usuario");
            return;
        }

        cargarDatos(usuario);
    }

    private void cargarDatos(User user) {

        firstName.setText(user.getNombre());
        lastName.setText(user.getApellidos());
        email.setText(user.getEmail());
        telefono.setText(user.getTelefono());
        password.setText(user.getPassword());

        if (user.getFechaNacimiento() != null) {
            dob.setValue(user.getFechaNacimiento().toLocalDate());
        }

        eleccionUsuario.setValue(user.getPerfil());

        if ("Male".equalsIgnoreCase(user.getGenero())) {
            rbMale.setSelected(true);
        } else {
            rbFemale.setSelected(true);
        }
    }

    @FXML
    private void updateUser() {

        if (usuario == null) {
            alerta("Error", "Primero selecciona un usuario");
            return;
        }

        if (firstName.getText().isEmpty() ||
            lastName.getText().isEmpty() ||
            email.getText().isEmpty() ||
            telefono.getText().isEmpty() ||
            password.getText().isEmpty() ||
            dob.getValue() == null) {

            alerta("Error", "Rellena todos los campos");
            return;
        }

        usuario.setNombre(firstName.getText());
        usuario.setApellidos(lastName.getText());
        usuario.setEmail(email.getText());
        usuario.setTelefono(telefono.getText());
        usuario.setPassword(password.getText());
        usuario.setFechaNacimiento(Date.valueOf(dob.getValue()));
        usuario.setGenero(rbMale.isSelected() ? "Male" : "Female");
        usuario.setPerfil(eleccionUsuario.getValue());

        userService.save(usuario);

        alerta("OK", "Usuario actualizado correctamente");

        tablaUsuarios.setItems(
            FXCollections.observableArrayList(userService.findAll())
        );
    }

    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.ADMIN);
    }

    private void alerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}