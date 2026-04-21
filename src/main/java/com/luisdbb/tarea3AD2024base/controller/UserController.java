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

@Controller
public class UserController implements Initializable {

    @FXML private Label userId;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private DatePicker dob;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private ChoiceBox<String> eleccionUsuario;

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Long> colUserId;
    @FXML private TableColumn<User, String> colFirstName;
    @FXML private TableColumn<User, String> colLastName;
    @FXML private TableColumn<User, Date> colDOB;
    @FXML private TableColumn<User, String> colGender;
    @FXML private TableColumn<User, String> colPerfil;
    @FXML private TableColumn<User, String> colEmail;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private UserService userService;

    private ObservableList<User> userList = FXCollections.observableArrayList();

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

    @FXML
    private void logout(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @FXML
    private void saveUser(ActionEvent event) {

        if (validate("Nombre", getNombre(), "[a-zA-Z]+")
                && validate("Apellidos", getApellidos(), "[a-zA-Z ]+")
                && emptyValidation("Fecha nacimiento", dob.getValue() == null)
                && validate("Email", getEmail(), "[a-zA-Z0-9._]+@[a-zA-Z0-9]+([.][a-zA-Z]+)+")
                && emptyValidation("Contraseña", getPassword().isEmpty())
                && eleccionUsuario.getValue() != null) {

            User user;

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

            // 🔥 CLAVE
            user.setPerfil(eleccionUsuario.getValue());

            userService.save(user);

            clearFields();
            loadUserDetails();
        }
    }

    private void setColumnProperties() {

        colUserId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPerfil.setCellValueFactory(new PropertyValueFactory<>("perfil"));
    }

    private void loadUserDetails() {
        userList.clear();
        userList.addAll(userService.findAll());
        userTable.setItems(userList);
    }

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

    public String getNombre() { return firstName.getText(); }
    public String getApellidos() { return lastName.getText(); }
    public LocalDate getDob() { return dob.getValue(); }
    public String getGenero() { return rbMale.isSelected() ? "Male" : "Female"; }
    public String getEmail() { return email.getText(); }
    public String getPassword() { return password.getText(); }

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

    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.matches()) return true;
        }
        validationAlert(field);
        return false;
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) return true;
        validationAlert(field);
        return false;
    }

    private void validationAlert(String field) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Campo incorrecto: " + field);
        alert.showAndWait();
    }
}