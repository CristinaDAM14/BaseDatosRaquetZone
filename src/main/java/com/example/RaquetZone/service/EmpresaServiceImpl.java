package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Empresa;
import com.example.RaquetZone.exception.EmpresaNotFoundException;
import com.example.RaquetZone.exception.HorarioNotFoundException;
import com.example.RaquetZone.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public Set<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    @Override
    public Set<Empresa> findByNomemp(String nomemp) {
        return empresaRepository.findByNomemp(nomemp);
    }

    @Override
    public Set<Empresa> findByEmailemp(String emailemp) {
        return empresaRepository.findByEmailemp(emailemp);
    }

    @Override
    public Set<Empresa> findByNomempAndEmailemp(String emailemp, String nomemp) {
        return empresaRepository.findByNomempAndEmailemp(emailemp, nomemp);
    }

    @Override
    public Optional<Empresa> findByCifemp(String cifemp) {
        return empresaRepository.findByCifemp(cifemp);
    }

    @Override
    public Empresa addEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public Empresa modifyEmpresa(String cifemp, Empresa newEmpresa) {
        Empresa empresa = empresaRepository.findByCifemp(cifemp).orElseThrow(() -> new EmpresaNotFoundException(cifemp));
        newEmpresa.setCifemp(empresa.getCifemp());
        return empresaRepository.save(newEmpresa);
    }

    @Override
    public void deleteByCifemp(String cifemp) {
        empresaRepository.findByCifemp(cifemp);
        empresaRepository.deleteById(cifemp);
    }
}

