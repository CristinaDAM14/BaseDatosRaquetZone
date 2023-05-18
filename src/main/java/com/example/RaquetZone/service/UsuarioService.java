package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface UsuarioService {

    Set<Usuario> findAll();
    Set <Usuario> recuperarPassword(String emailusr);
    Set<Usuario> findUsuariosByEmpresaCifempAndRolusr(String cifemp, int rolusr);
    Set<Usuario> findByNombreusr(String nombreusr);
    Optional<Usuario> findByDniusr(String dniusr);
    Set<Usuario> findByEmailusr(String emailusr);
    Set<Usuario> findByRolusr(int rolusr);
    Set<Usuario> findByDniusrAndPasswordusr(String dniusr, String passwordusr);
    Usuario addUsuario(Usuario usuario);
    Usuario modifyUsuario(String dniusr, Usuario newUsuario);
    void deleteUsuario(String dniusr);
}

