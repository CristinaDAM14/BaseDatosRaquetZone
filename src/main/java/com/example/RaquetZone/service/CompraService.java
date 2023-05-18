package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Compra;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public interface CompraService {

    Set<Compra> findAll();
    Optional<Compra> findByIdcomp(long idcomp);
    Set<Compra> findByFechacomp(Date fechacomp);
    Compra addCompra(Compra compra);
    Compra modifyCompra(long idcomp, Compra newCompra);
    void deleteCompra(long idcomp);
}
