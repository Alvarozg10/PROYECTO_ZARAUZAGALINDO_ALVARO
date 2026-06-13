package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Estado;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;

@Service
public class FCTService {

    @Autowired
    private FormacionEmpresaRepository formacionRepo;

    public void actualizarEstadosAutomaticamente() {

        LocalDate hoy = LocalDate.now();

        List<FormacionEmpresa> lista = formacionRepo.findAll();

        for (FormacionEmpresa fe : lista) {

            if (fe.getFechaFin() != null
                    && !fe.getFechaFin().toLocalDate().isAfter(hoy)
                    && fe.getEstado() != Estado.FINALIZADO) {

                fe.setEstado(Estado.FINALIZADO);

                formacionRepo.save(fe);
            }
        }
    }
}