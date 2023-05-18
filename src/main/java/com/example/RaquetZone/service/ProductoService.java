package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Producto;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.Optional;

@Service
public interface ProductoService {

    Set<Producto> findAll();
    Optional<Producto> findByIdprod(long idprod);
    Set<Producto> findByEmpresaCif(String cifemp);
    Set<Producto> findByCategoriaprodStockprod(String categoriaprod);
    Set<Producto> findByCategoriaprodPrecioprod(String categoriaprod);
    Set<Producto> findByNombreprod(String nombreprod);
    Producto addProducto(Producto producto);
    Producto modifyProducto(long idprod, Producto newProducto);
    void deleteProducto(long idprod);
}
