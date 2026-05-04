package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador encargado de la gestión completa de usuarios.
 * 
 * Permite crear, modificar y visualizar usuarios en una tabla,
 * incluyendo validación de datos antes de guardarlos en el sistema.
 */
@Controller
public class UserController implements Initializable {

    /** Identificador del usuario seleccionado */
    @FXML private Label userId;

    /** Campos del formulario */
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private DatePicker dob;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private ChoiceBox<String> eleccionUsuario;

    /** Tabla de usuarios */
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Long> colUserId;
    @FXML private TableColumn<User, String> colFirstName;
    @FXML private TableColumn<User, String> colLastName;
    @FXML private TableColumn<User, Date> colDOB;
    @FXML private TableColumn<User, String> colGender;
    @FXML private TableColumn<User, String> colPerfil;
    @FXML private TableColumn<User, String> colEmail;

    /** Gestor de navegación */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /** Servicio de usuarios */
    @Autowired
    private UserService userService;

    /** Lista observable de usuarios */
    private ObservableList<User> userList = FXCollections.observableArrayList();

    /**
     * Inicializa la vista configurando los valores iniciales
     * y cargando los datos en la tabla.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        eleccionUsuario.getItems().addAll(
                "ADMIN",
                "PROFESOR",
                "ESTUDIANTE",
                "TUTOR_EMPRESA"
        );

        eleccionUsuario.setValue("ESTUDIANTE");

        setColumnProperties();
        loadUserDetails();
    }

    /**
     * Cierra la sesión del usuario y vuelve al login.
     */
    @FXML
    private void logout(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    /**
     * Guarda o actualiza un usuario tras validar los datos introducidos.
     */
    @FXML
    private void saveUser(ActionEvent event) {

        if (validate("Nombre", getNombre(), "[a-zA-Z]+")
                && validate("Apellidos", getApellidos(), "[a-zA-Z ]+")
                && emptyValidation("Fecha nacimiento", dob.getValue() == null)
                && validate("Email", getEmail(), "[a-zA-Z0-9._]+@[a-zA-Z0-9]+([.][a-zA-Z]+)+")
                && emptyValidation("Contraseña", getPassword().isEmpty())
                && eleccionUsuario.getValue() != null) {

            User user;

            // Si no hay ID, se crea un nuevo usuario
            if (userId.getText() == null || userId.getText().isEmpty()) {
                user = new User();
            } else {
                user = userService.find(Long.parseLong(userId.getText()));
            }

            user.setNombre(getNombre());
            user.setApellidos(getApellidos());
            user.setGenero(getGenero());
            user.setEmail(getEmail());
            user.setPassword(getPassword());
            user.setFechaNacimiento(Date.valueOf(getDob()));

            // Asignación del perfil
            user.setPerfil(eleccionUsuario.getValue());

            userService.save(user);

            clearFields();
            loadUserDetails();
        }
    }

    /**
     * Configura las columnas de la tabla de usuarios.
     */
    private void setColumnProperties() {

        colUserId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPerfil.setCellValueFactory(new PropertyValueFactory<>("perfil"));
    }

    /**
     * Carga los usuarios desde la base de datos en la tabla.
     */
    private void loadUserDetails() {
        userList.clear();
        userList.addAll(userService.findAll());
        userTable.setItems(userList);
    }

    /**
     * Carga los datos de un usuario en el formulario para su edición.
     * 
     * @param user usuario seleccionado
     */
    private void updateUser(User user) {
        userId.setText(String.valueOf(user.getIdUsuario()));
        firstName.setText(user.getNombre());
        lastName.setText(user.getApellidos());
        dob.setValue(user.getFechaNacimiento().toLocalDate());
        email.setText(user.getEmail());
        password.setText(user.getPassword());

        if ("Male".equals(user.getGenero()))
            rbMale.setSelected(true);
        else
            rbFemale.setSelected(true);

        eleccionUsuario.setValue(user.getPerfil());
    }

    // Getters de los campos del formulario

    public String getNombre() { return firstName.getText(); }
    public String getApellidos() { return lastName.getText(); }
    public LocalDate getDob() { return dob.getValue(); }
    public String getGenero() { return rbMale.isSelected() ? "Male" : "Female"; }
    public String getEmail() { return email.getText(); }
    public String getPassword() { return password.getText(); }

    /**
     * Limpia los campos del formulario.
     */
    private void clearFields() {
        userId.setText(null);
        firstName.clear();
        lastName.clear();
        dob.setValue(null);
        rbMale.setSelected(true);
        eleccionUsuario.setValue("ESTUDIANTE");
        email.clear();
        password.clear();
    }

    /**
     * Valida un campo mediante una expresión regular.
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.matches()) return true;
        }
        validationAlert(field);
        return false;
    }

    /**
     * Valida que un campo no esté vacío.
     */
    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) return true;
        validationAlert(field);
        return false;
    }

    /**
     * Muestra una alerta de validación.
     */
    private void validationAlert(String field) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Campo incorrecto: " + field);
        alert.showAndWait();
    }
}