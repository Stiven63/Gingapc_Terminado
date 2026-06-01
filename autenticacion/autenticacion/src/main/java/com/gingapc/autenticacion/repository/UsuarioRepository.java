package com.gingapc.autenticacion.repository;

import com.gingapc.autenticacion.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // busca por email para el login y para validar duplicados
    Optional<Usuario> findByEmail(String email);
}
