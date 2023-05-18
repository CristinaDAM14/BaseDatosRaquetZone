package com.example.RaquetZone.repository;

import com.example.RaquetZone.domain.Producto_compra;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface Producto_compraRepository extends CrudRepository<Producto_compra,Long> {
    Set<Producto_compra> findAll();
    Optional<Producto_compra> findById(long idprodcomp);
}
