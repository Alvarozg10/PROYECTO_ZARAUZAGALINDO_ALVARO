package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

@Controller
public class UserController implements Initializable {

    @FXML private Button btnLogout;
    @FXML private Label userId;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private DatePicker dob;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;
    @FXML private ToggleGroup gender;
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private Button reset;
    @FXML private Button saveUser;

    @FXML private ChoiceBox<String> eleccionUsuario;

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Long> colUserId;
    @FXML private TableColumn<User, String> colFirstName;
    @FXML private TableColumn<User, String> colLastName;
    @FXML private TableColumn<User, LocalDate> colDOB;
    @FXML private TableColumn<User, String> colGender;
    @FXML private TableColumn<User, String> colRole;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, Boolean> colEdit;
    @FXML private MenuItem deleteUsers;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private UserService userService;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        eleccionUsuario.getItems().addAll(
                "Profesor",
                "Estudiante",
                "Tutor de empresa"
        );
        eleccionUsuario.setValue("Estudiante");

        userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setColumnProperties();
        loadUserDetails();
    }

    /* =========================
       BOTONES
       ========================= */

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @FXML
    private void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void saveUser(ActionEvent event) {

        if (validate("Nombre", getNombre(), "[a-zA-Z]+")
                && validate("Apellidos", getApellidos(), "[a-zA-Z ]+")
                && emptyValidation("Fecha nacimiento", dob.getEditor().getText().isEmpty())
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
            user.setContraseña(getPassword());
            user.setFechaNacimiento(Date.valueOf(getDob()));
            user.setRol(eleccionUsuario.getValue());

            userService.save(user);

            clearFields();
            loadUserDetails();
        }
    }

    @FXML
    private void deleteUsers(ActionEvent event) {

        List<User> users = userTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Eliminar usuarios seleccionados?");

        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent() && action.get() == ButtonType.OK) {
            userService.deleteInBatch(users);
            loadUserDetails();
        }
    }

    /* =========================
       TABLA
       ========================= */

    private void setColumnProperties() {

        colUserId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactory = param -> new TableCell<>() {

        Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
        final Button btnEdit = new Button();

        @Override
        protected void updateItem(Boolean empty, boolean isEmpty) {
            super.updateItem(empty, isEmpty);

            if (isEmpty) {
                setGraphic(null);
            } else {
                btnEdit.setOnAction(e -> updateUser(getTableView().getItems().get(getIndex())));
                btnEdit.setStyle("-fx-background-color: transparent;");

                ImageView iv = new ImageView(imgEdit);
                iv.setPreserveRatio(true);
                iv.setFitHeight(16);

                btnEdit.setGraphic(iv);
                setGraphic(btnEdit);
                setAlignment(Pos.CENTER);
            }
        }
    };

    private void updateUser(User user) {

        userId.setText(String.valueOf(user.getIdUsuario()));
        firstName.setText(user.getNombre());
        lastName.setText(user.getApellidos());
        dob.setValue(user.getFechaNacimiento().toLocalDate());

        if ("Male".equals(user.getGenero()))
            rbMale.setSelected(true);
        else
            rbFemale.setSelected(true);

        eleccionUsuario.setValue(user.getRol());
    }

    private void loadUserDetails() {
        userList.clear();
        userList.addAll(userService.findAll());
        userTable.setItems(userList);
    }

    /* =========================
       GETTERS
       ========================= */

    public String getNombre() { return firstName.getText(); }
    public String getApellidos() { return lastName.getText(); }
    public LocalDate getDob() { return dob.getValue(); }
    public String getGenero() { return rbMale.isSelected() ? "Male" : "Female"; }
    public String getEmail() { return email.getText(); }
    public String getPassword() { return password.getText(); }

    /* =========================
       UTILIDADES
       ========================= */

    private void clearFields() {
        userId.setText(null);
        firstName.clear();
        lastName.clear();
        dob.getEditor().clear();
        rbMale.setSelected(true);
        rbFemale.setSelected(false);
        eleccionUsuario.setValue("Estudiante");
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
        alert.setTitle("Error de validación");
        alert.setHeaderText(null);
        alert.setContentText("Campo incorrecto: " + field);
        alert.showAndWait();
    }
}

