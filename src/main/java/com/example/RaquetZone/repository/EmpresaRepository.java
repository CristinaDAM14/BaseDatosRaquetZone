package com.example.RaquetZone.repository;

import com.example.RaquetZone.domain.Empresa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface EmpresaRepository  extends CrudRepository<Empresa, String> {

    Set<Empresa> findAll();
    Set<Empresa> findByNomemp(String nomemp);
    Set<Empresa> findByEmailemp(String emailemp);
    Optional<Empresa> findByCifemp(String cifemp);
    Set<Empresa> findByNomempAndEmailemp(String emailemp, String nomemp);

}

