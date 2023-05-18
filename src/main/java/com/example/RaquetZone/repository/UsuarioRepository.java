package com.example.RaquetZone.repository;

import com.example.RaquetZone.domain.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String> {

    Set<Usuario> findAll();
    Set<Usuario> findByNombreusr(String nombreusr);
    Optional<Usuario> findByDniusr(String dniusr);
    Set<Usuario> findByEmailusr(String emailusr);
    Set<Usuario> findByRolusr(int rolusr);
    Set<Usuario> findByDniusrAndPasswordusr(String dniusr, String passwordusr);
    @Query(value = "SELECT * FROM Usuario u WHERE u.email = :email",nativeQuery = true)
    Set <Usuario> recuperarPassword(@Param("email") String emailusr);
    Set<Usuario> findUsuariosByEmpresaCifempAndRolusr(String cifemp, int rolusr);
}

