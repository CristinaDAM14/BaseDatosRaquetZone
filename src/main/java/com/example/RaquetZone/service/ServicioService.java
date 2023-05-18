package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Servicio;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface ServicioService {

    Set<Servicio> findAll();
    Optional<Servicio> findByIdserv(long idserv);
    Set<Servicio> findByPrecioserv();
    Set<Servicio> findByUnidadestiemposerv();
    Set<Servicio> findByEmpresaCif(String cifemp);
    Set<Servicio> findByDescuentoserv();
    Servicio addServicio(Servicio servicio);
    Servicio modifyServicio(long idserv,Servicio newServicio);
    void deleteServicio(long idserv);
}
