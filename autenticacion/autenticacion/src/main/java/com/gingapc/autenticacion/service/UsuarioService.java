package com.gingapc.autenticacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gingapc.autenticacion.dto.LoginRequest;
import com.gingapc.autenticacion.dto.RegistroRequest;
import com.gingapc.autenticacion.model.Usuario;
import com.gingapc.autenticacion.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // registrar usuario nuevo
    public String registrar(RegistroRequest request) {

        // verificar que el correo no este registrado antes
        Optional<Usuario> existe = usuarioRepository.findByEmail(request.getEmail());
        if (existe.isPresent()) {
            throw new RuntimeException("El correo ya esta registrado");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(request.getEmail());

        // bcrypt encripta la contraseña, no se guarda en texto plano
        nuevoUsuario.setPassword(passwordEncoder.encode(request.getPassword()));

        usuarioRepository.save(nuevoUsuario);
        return "Usuario registrado correctamente";
    }

    // iniciar sesion
    public String login(LoginRequest request) {

        // buscar el usuario por correo
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Correo o contrasena incorrectos"));

        // comparar la contrasena ingresada con la encriptada en la bd
        boolean contrasenaCorrecta = passwordEncoder.matches(request.getPassword(), usuario.getPassword());

        if (!contrasenaCorrecta) {
            throw new RuntimeException("Correo o contrasena incorrectos");
        }

        return "Bienvenido a Ginga PC, " + usuario.getEmail() + " - Rol: " + usuario.getRol();
    }

    // listar todos los usuarios
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // buscar usuario por id
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    // eliminar usuario
    public String eliminar(Long id) {
        Usuario usuario = obtenerPorId(id);
        usuarioRepository.delete(usuario);
        return "Usuario eliminado correctamente";
    }
}
