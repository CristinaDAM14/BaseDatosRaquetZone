package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Compra;
import com.example.RaquetZone.exception.CompraNotFoundException;
import com.example.RaquetZone.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class CompraServiceImpl implements CompraService{

    @Autowired
    private CompraRepository compraRepository;

    @Override
    public Set<Compra> findAll() {
        return compraRepository.findAll();
    }

    @Override
    public Optional<Compra> findByIdcomp(long idcomp) {
        return compraRepository.findByIdcomp(idcomp);
    }

    @Override
    public Set<Compra> findByFechacomp(Date fechacomp) {
        return compraRepository.findByFechacomp(fechacomp);
    }

    @Override
    public Compra addCompra(Compra compra) {
        return compraRepository.save(compra);
    }

    @Override
    public Compra modifyCompra(long idcomp, Compra newCompra) {
        Compra compra = compraRepository.findByIdcomp(idcomp).orElseThrow(() -> new CompraNotFoundException(idcomp));
        newCompra.setIdcomp(compra.getIdcomp());
        return compraRepository.save(newCompra);
    }

    @Override
    public void deleteCompra(long idcomp) {
        compraRepository.findByIdcomp(idcomp).orElseThrow(() -> new CompraNotFoundException(idcomp));
        compraRepository.deleteById(idcomp);
    }
}
