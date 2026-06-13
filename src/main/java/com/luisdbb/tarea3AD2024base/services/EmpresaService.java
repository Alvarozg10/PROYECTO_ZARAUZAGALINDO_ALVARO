package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Empresa;
import com.luisdbb.tarea3AD2024base.repositorios.EmpresaRepository;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa guardar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public List<Empresa> obtenerTodas() {
        return empresaRepository.findAll();
    }
}