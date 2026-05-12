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
import com.luisdbb.tarea3AD2024base.modelo.Ciclo;
import com.luisdbb.tarea3AD2024base.modelo.Curso;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador encargado de la gestión completa de usuarios.
 * 
 * Permite crear, modificar y visualizar usuarios en una tabla.
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
    @FXML private TextField telefono;

    @FXML private ChoiceBox<String> eleccionUsuario;
    @FXML private ChoiceBox<Curso> cbCurso;
    @FXML private ChoiceBox<Ciclo> cbCiclo;

    /** Tabla de usuarios */
    @FXML private TableView<User> userTable;

    @FXML private TableColumn<User, Long> colUserId;
    @FXML private TableColumn<User, String> colFirstName;
    @FXML private TableColumn<User, String> colLastName;
    @FXML private TableColumn<User, Date> colDOB;
    @FXML private TableColumn<User, String> colGender;
    @FXML private TableColumn<User, String> colPerfil;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colTelefono;
    @FXML private TableColumn<User, String> colCurso;
    @FXML private TableColumn<User, String> colCiclo;

    /** Gestor de navegación */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /** Servicio de usuarios */
    @Autowired
    private UserService userService;

    /** Lista observable */
    private ObservableList<User> userList =
            FXCollections.observableArrayList();

    /**
     * Inicializa la vista.
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

        cbCurso.getItems().addAll(Curso.values());
        cbCiclo.getItems().addAll(Ciclo.values());

        rbMale.setSelected(true);

        setColumnProperties();

        loadUserDetails();

        // Selección automática desde tabla
        userTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {

                    if (newSelection != null) {
                        updateUser(newSelection);
                    }
                });
    }

    /**
     * Cierra sesión.
     */
    @FXML
    private void logout(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    /**
     * Guarda o actualiza usuario.
     */
    @FXML
    private void saveUser(ActionEvent event) {

        if (validate(
                    "Nombre",
                    getNombre(),
                    "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"
                )

                && validate(
                    "Apellidos",
                    getApellidos(),
                    "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"
                )

                && emptyValidation(
                    "Fecha nacimiento",
                    dob.getValue() == null
                )

                && validate(
                    "Email",
                    getEmail(),
                    "[a-zA-Z0-9._]+@[a-zA-Z0-9]+([.][a-zA-Z]+)+"
                )

                && emptyValidation(
                    "Contraseña",
                    getPassword().isEmpty()
                )

                && validate(
                    "Teléfono",
                    telefono.getText(),
                    "\\d+"
                )

                && eleccionUsuario.getValue() != null
                && cbCurso.getValue() != null
                && cbCiclo.getValue() != null) {

            User user;

            // Crear o actualizar
            if (userId.getText() == null ||
                userId.getText().isEmpty()) {

                user = new User();

            } else {

                user = userService.find(
                    Long.parseLong(userId.getText())
                );
            }

            user.setNombre(getNombre());
            user.setApellidos(getApellidos());
            user.setGenero(getGenero());
            user.setEmail(getEmail());
            user.setPassword(getPassword());
            user.setTelefono(telefono.getText());

            user.setFechaNacimiento(
                Date.valueOf(getDob())
            );

            user.setPerfil(eleccionUsuario.getValue());

            user.setCurso(cbCurso.getValue());
            user.setCiclo(cbCiclo.getValue());

            userService.save(user);

            clearFields();

            loadUserDetails();
        }
    }

    /**
     * Configura columnas de tabla.
     */
    private void setColumnProperties() {

        colUserId.setCellValueFactory(
            new PropertyValueFactory<>("idUsuario")
        );

        colFirstName.setCellValueFactory(
            new PropertyValueFactory<>("nombre")
        );

        colLastName.setCellValueFactory(
            new PropertyValueFactory<>("apellidos")
        );

        colDOB.setCellValueFactory(
            new PropertyValueFactory<>("fechaNacimiento")
        );

        colGender.setCellValueFactory(
            new PropertyValueFactory<>("genero")
        );

        colEmail.setCellValueFactory(
            new PropertyValueFactory<>("email")
        );

        colPerfil.setCellValueFactory(
            new PropertyValueFactory<>("perfil")
        );

        colTelefono.setCellValueFactory(
            new PropertyValueFactory<>("telefono")
        );

        colCurso.setCellValueFactory(
            new PropertyValueFactory<>("curso")
        );

        colCiclo.setCellValueFactory(
            new PropertyValueFactory<>("ciclo")
        );
    }

    /**
     * Carga usuarios.
     */
    private void loadUserDetails() {

        userList.clear();

        userList.addAll(userService.findAll());

        userTable.setItems(userList);
    }

    /**
     * Carga datos del usuario seleccionado.
     */
    private void updateUser(User user) {

        userId.setText(
            String.valueOf(user.getIdUsuario())
        );

        firstName.setText(user.getNombre());

        lastName.setText(user.getApellidos());

        dob.setValue(
            user.getFechaNacimiento().toLocalDate()
        );

        email.setText(user.getEmail());

        password.setText(user.getPassword());

        telefono.setText(user.getTelefono());

        if ("Male".equals(user.getGenero())) {
            rbMale.setSelected(true);
        } else {
            rbFemale.setSelected(true);
        }

        eleccionUsuario.setValue(user.getPerfil());

        cbCurso.setValue(user.getCurso());

        cbCiclo.setValue(user.getCiclo());
    }

    // Getters

    public String getNombre() {
        return firstName.getText();
    }

    public String getApellidos() {
        return lastName.getText();
    }

    public LocalDate getDob() {
        return dob.getValue();
    }

    public String getGenero() {
        return rbMale.isSelected()
                ? "Male"
                : "Female";
    }

    public String getEmail() {
        return email.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    /**
     * Limpia formulario.
     */
    private void clearFields() {

        userId.setText("");

        firstName.clear();

        lastName.clear();

        dob.setValue(null);

        rbMale.setSelected(true);

        eleccionUsuario.setValue("ESTUDIANTE");

        email.clear();

        password.clear();

        telefono.clear();

        cbCurso.setValue(null);

        cbCiclo.setValue(null);
    }

    /**
     * Validación regex.
     */
    private boolean validate(
            String field,
            String value,
            String pattern) {

        if (!value.isEmpty()) {

            Pattern p = Pattern.compile(pattern);

            Matcher m = p.matcher(value);

            if (m.matches()) {
                return true;
            }
        }

        validationAlert(field);

        return false;
    }

    /**
     * Validación vacíos.
     */
    private boolean emptyValidation(
            String field,
            boolean empty) {

        if (!empty) {
            return true;
        }

        validationAlert(field);

        return false;
    }

    /**
     * Alerta de validación.
     */
    private void validationAlert(String field) {

        Alert alert =
                new Alert(AlertType.WARNING);

        alert.setTitle("Error");

        alert.setHeaderText(null);

        alert.setContentText(
            "Campo incorrecto: " + field
        );

        alert.showAndWait();
    }
}