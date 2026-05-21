package com.luisdbb.tarea3AD2024base.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;
import com.luisdbb.tarea3AD2024base.modelo.Ciclo;
import com.luisdbb.tarea3AD2024base.modelo.Curso;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador encargado de gestionar la modificación de usuarios.
 * 
 * Permite visualizar los usuarios en una tabla, seleccionar uno
 * y editar sus datos personales, guardando los cambios en el sistema.
 */
@Controller
public class ModificarUsuarioController {

    /** Tabla que muestra los usuarios */
    @FXML private TableView<User> tablaUsuarios;

    @FXML private TableColumn<User, String> colNombre;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colPerfil;

    /** Campos del formulario de edición */
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField telefono;
    @FXML private PasswordField password;
    @FXML private ChoiceBox<String> eleccionUsuario;
    @FXML private DatePicker dob;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;
    @FXML private ChoiceBox<Curso> cbCurso;
    @FXML private ChoiceBox<Ciclo> cbCiclo;

    /** Grupo de selección para el género */
    private ToggleGroup gender;

    /** Usuario actualmente seleccionado para modificar */
    private User usuario;

    /** Servicio de gestión de usuarios */
    @Autowired
    private UserService userService;

    /** Gestor de navegación entre vistas */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la tabla y configura los eventos de selección.
     * 
     * Permite seleccionar un usuario haciendo doble clic en la tabla.
     */
    @FXML
    public void initialize() {

        tablaUsuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPerfil.setCellValueFactory(new PropertyValueFactory<>("perfil"));

        tablaUsuarios.setItems(
            FXCollections.observableArrayList(userService.findAll())
        );

        eleccionUsuario.getItems().addAll(
                "PROFESOR", "ESTUDIANTE", "TUTOR_EMPRESA"
        );
        
        cbCurso.getItems().addAll(Curso.values());
        cbCiclo.getItems().addAll(Ciclo.values());

        gender = new ToggleGroup();
        rbMale.setToggleGroup(gender);
        rbFemale.setToggleGroup(gender);

        // Permite cargar el usuario con doble clic en la tabla
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

    /**
     * Carga el usuario seleccionado desde la tabla.
     */
    @FXML
    private void cargarUsuario() {

        usuario = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuario == null) {
            alerta("Error", "Selecciona un usuario");
            return;
        }

        cargarDatos(usuario);
    }

    /**
     * Rellena el formulario con los datos del usuario seleccionado.
     * 
     * @param user usuario cuyos datos se van a mostrar
     */
    private void cargarDatos(User user) {

        firstName.setText(user.getNombre() != null ? user.getNombre() : "");
        lastName.setText(user.getApellidos() != null ? user.getApellidos() : "");
        email.setText(user.getEmail() != null ? user.getEmail() : "");
        telefono.setText(user.getTelefono() != null ? user.getTelefono() : "");
        password.setText(user.getPassword() != null ? user.getPassword() : "");
        cbCurso.setValue(user.getCurso());
        cbCiclo.setValue(user.getCiclo());

        if (user.getFechaNacimiento() != null) {
            dob.setValue(user.getFechaNacimiento().toLocalDate());
        } else {
            dob.setValue(null);
        }

        eleccionUsuario.setValue(user.getPerfil());

        if ("Male".equalsIgnoreCase(user.getGenero())) {
            rbMale.setSelected(true);
        } else {
            rbFemale.setSelected(true);
        }
    }

    /**
     * Valida los datos introducidos y actualiza el usuario seleccionado.
     */
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
        	    dob.getValue() == null ||
        	    cbCurso.getValue() == null ||
        	    cbCiclo.getValue() == null) {

            alerta("Error", "Rellena todos los campos");
            return;
        }

        usuario.setNombre(firstName.getText());
        usuario.setApellidos(lastName.getText());
        usuario.setEmail(email.getText());
        usuario.setTelefono(telefono.getText());
        usuario.setPassword(password.getText());
        usuario.setFechaNacimiento(Date.valueOf(dob.getValue()));
        usuario.setGenero(rbMale.isSelected() ? "Hombre" : "Mujer");
        usuario.setPerfil(eleccionUsuario.getValue());
        usuario.setCurso(cbCurso.getValue());
        usuario.setCiclo(cbCiclo.getValue());

        userService.save(usuario);

        alerta("OK", "Usuario actualizado correctamente");

        tablaUsuarios.setItems(
            FXCollections.observableArrayList(userService.findAll())
        );
    }

    /**
     * Vuelve al panel de administrador.
     */
    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.ADMIN);
    }

    /**
     * Muestra una alerta informativa al usuario.
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