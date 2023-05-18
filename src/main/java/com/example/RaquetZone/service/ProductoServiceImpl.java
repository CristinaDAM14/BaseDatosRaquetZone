package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Producto;
import com.example.RaquetZone.exception.ProductoNotFoundException;
import com.example.RaquetZone.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Set<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> findByIdprod(long idprod){
        return productoRepository.findByIdprod(idprod);
    }

    @Override
    public Set<Producto> findByEmpresaCif(String cifemp) {
        return productoRepository.findByEmpresaCif(cifemp);
    }

    @Override
    public Set<Producto> findByCategoriaprodStockprod(String categoriaprod) {
        return productoRepository.findByCategoriaprodStockprod(categoriaprod);
    }

    @Override
    public Set<Producto> findByCategoriaprodPrecioprod(String categoriaprod) {
        return productoRepository.findByCategoriaprodPrecioprod(categoriaprod);
    }

    @Override
    public Set<Producto> findByNombreprod(String nombreprod) {
        return productoRepository.findByNombreprod(nombreprod);
    }

    @Override
    public Producto addProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto modifyProducto(long idprod, Producto newProducto) {
        Producto producto = productoRepository.findByIdprod(idprod).orElseThrow(() -> new ProductoNotFoundException(idprod));
        newProducto.setIdprod(producto.getIdprod());
        return productoRepository.save(newProducto);
    }

    @Override
    public void deleteProducto(long idprod) {
        productoRepository.findByIdprod(idprod).orElseThrow(() -> new ProductoNotFoundException(idprod));
        productoRepository.deleteById(idprod);
    }
}
