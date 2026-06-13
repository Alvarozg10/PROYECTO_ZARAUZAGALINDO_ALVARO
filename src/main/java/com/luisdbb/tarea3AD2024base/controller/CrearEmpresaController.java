package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Empresa;
import com.luisdbb.tarea3AD2024base.repositorios.EmpresaRepository;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

@Controller
public class CrearEmpresaController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtCorreo;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private void guardarEmpresa() {

        if (txtNombre.getText().isBlank()
                || txtDireccion.getText().isBlank()
                || txtTelefono.getText().isBlank()
                || txtCorreo.getText().isBlank()) {

            alerta("Error", "Rellena todos los campos");
            return;
        }

        Empresa empresa = new Empresa();

        empresa.setNombre(txtNombre.getText());
        empresa.setDireccion(txtDireccion.getText());
        empresa.setTelefono(txtTelefono.getText());
        empresa.setCorreoElectronico(txtCorreo.getText());

        empresaRepository.save(empresa);

        alerta("Correcto", "Empresa creada correctamente");

        limpiarCampos();
    }

    private void limpiarCampos() {

        txtNombre.clear();
        txtDireccion.clear();
        txtTelefono.clear();
        txtCorreo.clear();
    }

    @FXML
    private void volver() {

        stageManager.switchScene(FxmlView.PROFESOR);
    }

    private void alerta(String titulo, String mensaje) {

        Alert a = new Alert(Alert.AlertType.INFORMATION);

        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);

        a.showAndWait();
    }
}