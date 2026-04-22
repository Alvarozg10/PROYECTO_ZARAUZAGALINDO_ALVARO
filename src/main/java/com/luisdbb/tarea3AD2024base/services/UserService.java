package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FormacionEmpresaRepository formacionRepository;

    public User save(User entity) {
        return userRepository.save(entity);
    }

    public User find(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && password.equals(user.getPassword())) {
            return user;
        }

        return null;
    }

    public List<FormacionEmpresa> findAllFormaciones() {
        return formacionRepository.findAll();
    }

    public List<FormacionEmpresa> findFormacionesByTutor(User tutor) {
        return formacionRepository.findByTutor(tutor);
    }

    public List<FormacionEmpresa> findFormacionesByEstudiante(User estudiante) {
        return formacionRepository.findByEstudiante(estudiante);
    }

    public FormacionEmpresa findFormacionUnicaByEstudiante(User estudiante) {
        return formacionRepository.findByEstudiante(estudiante)
                .stream()
                .findFirst()
                .orElse(null);
    }
    
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}