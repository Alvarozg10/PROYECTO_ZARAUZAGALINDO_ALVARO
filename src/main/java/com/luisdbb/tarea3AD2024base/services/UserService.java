package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.UserRepository;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con los usuarios.
 * 
 * Proporciona métodos para realizar operaciones CRUD sobre usuarios,
 * autenticación y acceso a las formaciones en empresa (FCT).
 */
@Service
public class UserService {

    /** Repositorio de usuarios */
    @Autowired
    private UserRepository userRepository;

    /** Repositorio de formaciones FCT */
    @Autowired
    private FormacionEmpresaRepository formacionRepository;

    /**
     * Guarda o actualiza un usuario en la base de datos.
     * 
     * @param entity usuario a guardar
     * @return usuario guardado
     */
    public User save(User entity) {
        return userRepository.save(entity);
    }

    /**
     * Busca un usuario por su identificador.
     * 
     * @param id identificador del usuario
     * @return usuario encontrado o null si no existe
     */
    public User find(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Obtiene todos los usuarios del sistema.
     * 
     * @return lista de usuarios
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Autentica un usuario mediante email y contraseña.
     * 
     * @param email email del usuario
     * @param password contraseña del usuario
     * @return usuario autenticado o null si las credenciales son incorrectas
     */
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && password.equals(user.getPassword())) {
            return user;
        }

        return null;
    }

    /**
     * Obtiene todas las formaciones en empresa (FCT).
     * 
     * @return lista de formaciones
     */
    public List<FormacionEmpresa> findAllFormaciones() {
        return formacionRepository.findAll();
    }

    /**
     * Obtiene las formaciones asociadas a un tutor.
     * 
     * @param tutor usuario con rol de tutor
     * @return lista de formaciones del tutor
     */
    public List<FormacionEmpresa> findFormacionesByTutor(User tutor) {
        return formacionRepository.findByTutor(tutor);
    }

    /**
     * Obtiene las formaciones asociadas a un estudiante.
     * 
     * @param estudiante usuario con rol de estudiante
     * @return lista de formaciones del estudiante
     */
    public List<FormacionEmpresa> findFormacionesByEstudiante(User estudiante) {
        return formacionRepository.findByEstudiante(estudiante);
    }

    /**
     * Obtiene una única formación asociada a un estudiante.
     * 
     * @param estudiante usuario estudiante
     * @return formación encontrada o null si no existe
     */
    public FormacionEmpresa findFormacionUnicaByEstudiante(User estudiante) {
        return formacionRepository.findByEstudiante(estudiante)
                .stream()
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Elimina un usuario por su identificador.
     * 
     * @param id identificador del usuario
     */
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}