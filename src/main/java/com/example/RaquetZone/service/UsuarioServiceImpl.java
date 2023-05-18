package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Usuario;
import com.example.RaquetZone.exception.UsuarioNotFoundException;
import com.example.RaquetZone.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Set<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Set<Usuario> recuperarPassword(String emailusr){return usuarioRepository.recuperarPassword(emailusr);}

    @Override
    public Set<Usuario> findUsuariosByEmpresaCifempAndRolusr(String cifemp, int rolusr) {
        return usuarioRepository.findUsuariosByEmpresaCifempAndRolusr( cifemp, rolusr);
    }

    @Override
    public Set<Usuario> findByNombreusr(String nombreusr) {
        return usuarioRepository.findByNombreusr(nombreusr);
    }

    @Override
    public Optional<Usuario> findByDniusr(String dniusr) {
        return usuarioRepository.findByDniusr(dniusr);
    }

    @Override
    public Set<Usuario> findByEmailusr(String emailusr) {
        return usuarioRepository.findByEmailusr(emailusr);
    }

    @Override
    public Set<Usuario> findByRolusr(int rolusr) {
        return usuarioRepository.findByRolusr(rolusr);
    }

    @Override
    public Set<Usuario> findByDniusrAndPasswordusr(String dniusr, String passwordusr) {
        return usuarioRepository.findByDniusrAndPasswordusr(dniusr, passwordusr);
    }

    @Override
    public Usuario addUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario modifyUsuario(String dniusr, Usuario newUsuario) {
        Usuario usuario = usuarioRepository.findByDniusr(dniusr).orElseThrow(() -> new UsuarioNotFoundException(dniusr));
        newUsuario.setDniusr(usuario.getDniusr());
        return usuarioRepository.save(newUsuario);
    }

    @Override
    public void deleteUsuario(String dniusr) {
        usuarioRepository.findByDniusr(dniusr)
                .orElseThrow(() -> new UsuarioNotFoundException(dniusr));
        usuarioRepository.deleteById(dniusr);
    }
}
