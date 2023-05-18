package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Servicio;
import com.example.RaquetZone.exception.ServicioNotFoundException;
import com.example.RaquetZone.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ServicioServiceImpl implements ServicioService{

    @Autowired
    ServicioRepository servicioRepository;

    @Override
    public Set<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    @Override
    public Optional<Servicio> findByIdserv(long idserv) {
        return servicioRepository.findByIdserv(idserv);
    }

    @Override
    public Set<Servicio> findByPrecioserv() {
        return servicioRepository.findByPrecioserv();
    }

    @Override
    public Set<Servicio> findByUnidadestiemposerv() {
        return servicioRepository.findByUnidadestiemposerv();
    }

    @Override
    public Set<Servicio> findByEmpresaCif(String cifemp) {
        return servicioRepository.findByEmpresaCif(cifemp);
    }

    @Override
    public Set<Servicio> findByDescuentoserv() {
        return servicioRepository.findByDescuentoserv();
    }

    @Override
    public Servicio addServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio modifyServicio(long idserv, Servicio newServicio) {
        Servicio servicio = servicioRepository.findByIdserv(idserv).orElseThrow(() -> new ServicioNotFoundException(idserv));
        newServicio.setIdserv(servicio.getIdserv());
        return servicioRepository.save(newServicio);
    }

    @Override
    public void deleteServicio(long idserv) {
        servicioRepository.findByIdserv(idserv)
                .orElseThrow(() -> new ServicioNotFoundException(idserv));
        servicioRepository.deleteById(idserv);

    }
}
