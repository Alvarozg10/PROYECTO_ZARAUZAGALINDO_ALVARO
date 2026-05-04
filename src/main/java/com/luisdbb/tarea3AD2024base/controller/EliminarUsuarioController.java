package com.luisdbb.tarea3AD2024base.controller;

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

/**
 * Controlador encargado de gestionar la eliminación de usuarios.
 * 
 * Permite visualizar los usuarios del sistema en una tabla y eliminar
 * el usuario seleccionado previa confirmación.
 */
@Controller
public class EliminarUsuarioController {

    /** Tabla que muestra los usuarios */
    @FXML private TableView<User> tablaUsuarios;

    @FXML private TableColumn<User, String> colNombre;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colPerfil;

    /** Servicio de gestión de usuarios */
    @Autowired
    private UserService userService;

    /** Gestor de navegación entre vistas */
    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Inicializa la tabla configurando las columnas
     * y cargando los usuarios del sistema.
     */
    @FXML
    public void initialize() {

        tablaUsuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPerfil.setCellValueFactory(new PropertyValueFactory<>("perfil"));

        cargarUsuarios();
    }

    /**
     * Carga todos los usuarios desde la base de datos
     * y los muestra en la tabla.
     */
    private void cargarUsuarios() {
        tablaUsuarios.setItems(
            FXCollections.observableArrayList(userService.findAll())
        );
    }

    /**
     * Elimina el usuario seleccionado tras confirmar la acción.
     * 
     * Muestra una ventana de confirmación antes de proceder
     * a la eliminación del usuario.
     */
    @FXML
    private void eliminarUsuario() {

        User usuario = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuario == null) {
            alerta("Error", "Selecciona un usuario");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText("¿Seguro que quieres eliminar este usuario?");
        confirm.setContentText(usuario.getNombre());

        confirm.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {

                userService.delete(usuario.getIdUsuario());

                alerta("OK", "Usuario eliminado");

                cargarUsuarios(); 
            }
        });
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