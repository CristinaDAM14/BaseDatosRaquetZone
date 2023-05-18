package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Producto_compra;
import com.example.RaquetZone.repository.Producto_compraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class Producto_compraServiceImpl implements Producto_compraService{

    @Autowired
    Producto_compraRepository producto_compraRepository;

    @Override
    public Set<Producto_compra> findAll() {
        return producto_compraRepository.findAll();
    }

    @Override
    public Producto_compra addProductoCompra(Producto_compra producto_compra) {
        return producto_compraRepository.save(producto_compra);
    }
}
