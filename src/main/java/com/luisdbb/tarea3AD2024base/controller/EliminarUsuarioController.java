package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controlador encargado de eliminar usuarios.
 */
@Controller
public class EliminarUsuarioController {

    /** Tabla usuarios */
    @FXML
    private TableView<User> tablaUsuarios;

    @FXML
    private TableColumn<User, String> colNombre;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, String> colPerfil;

    /** Servicio usuarios */
    @Autowired
    private UserService userService;

    /** Navegación */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la tabla.
     */
    @FXML
    public void initialize() {

        tablaUsuarios.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        // Nombre + curso + ciclo
        colNombre.setCellValueFactory(data -> {

            User u = data.getValue();

            return new SimpleStringProperty(
                    u.getNombre()
                    + " "
                    + u.getApellidos()
            );
        });

        // Email
        colEmail.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getEmail()
                )
        );

        // Perfil
        colPerfil.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getPerfil()
                )
        );

        cargarUsuarios();
    }

    /**
     * Carga usuarios.
     */
    private void cargarUsuarios() {

        tablaUsuarios.setItems(
                FXCollections.observableArrayList(
                        userService.findAll()
                )
        );
    }

    /**
     * Elimina el usuario seleccionado.
     */
    @FXML
    private void eliminarUsuario() {

        User usuario = tablaUsuarios
                .getSelectionModel()
                .getSelectedItem();

        if (usuario == null) {

            alerta(
                    "Error",
                    "Selecciona un usuario"
            );

            return;
        }

        Alert confirm = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirm.setTitle("Confirmar eliminación");

        confirm.setHeaderText(
                "¿Seguro que quieres eliminar este usuario?"
        );

        confirm.setContentText(
                usuario.getNombre()
                        + " "
                        + usuario.getApellidos()
        );

        confirm.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {

                userService.delete(
                        usuario.getIdUsuario()
                );

                alerta(
                        "OK",
                        "Usuario eliminado"
                );

                cargarUsuarios();
            }
        });
    }

    /**
     * Vuelve al panel admin.
     */
    @FXML
    private void volver() {
        stageManager.switchScene(FxmlView.ADMIN);
    }

    /**
     * Muestra alerta.
     */
    private void alerta(String t, String m) {

        Alert a = new Alert(
                Alert.AlertType.INFORMATION
        );

        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);

        a.showAndWait();
    }
}