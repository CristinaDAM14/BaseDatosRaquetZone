package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Empresa;
import com.example.RaquetZone.domain.Producto_compra;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface Producto_compraService {

    Set<Producto_compra> findAll();
    Producto_compra addProductoCompra(Producto_compra producto_compra);
}
