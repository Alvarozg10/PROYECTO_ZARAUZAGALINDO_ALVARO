package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User entity) {
        return userRepository.save(entity);
    }

    public User update(User entity) {
        return userRepository.save(entity);
    }

    public void delete(User entity) {
        userRepository.delete(entity);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
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

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteInBatch(List<User> users) {
        userRepository.deleteAll(users);
    }

    public List<Estudiante> findEstudiantesByTutor(Long tutorId) {
        return userRepository.findByTutorEmpresa_IdUsuario(tutorId);
    }
}
