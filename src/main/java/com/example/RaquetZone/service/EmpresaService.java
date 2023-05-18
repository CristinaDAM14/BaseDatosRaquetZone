package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Empresa;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface EmpresaService {

    Set<Empresa> findAll();
    Set<Empresa> findByNomemp(String Nomemp);
    Set<Empresa> findByEmailemp(String emailemp);
    Optional<Empresa> findByCifemp(String cifemp);
    Set<Empresa> findByNomempAndEmailemp(String emailemp, String nomemp);
    Empresa addEmpresa(Empresa empresa);
    Empresa modifyEmpresa(String cifemp, Empresa newEmpresa);
    void deleteByCifemp(String cifemp);
}
