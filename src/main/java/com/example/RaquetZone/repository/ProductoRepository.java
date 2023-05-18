package com.example.RaquetZone.repository;

import com.example.RaquetZone.domain.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {

    Set<Producto> findAll();
    Optional<Producto> findByIdprod (Long idprod);
    @Query(value = "SELECT * FROM Producto p WHERE p.categoria = :categoria ORDER BY p.stock",nativeQuery = true)
    Set<Producto> findByCategoriaprodStockprod(@Param("categoria")String catgeoriaprod);
    @Query(value = "SELECT * FROM Producto p WHERE p.categoria = :categoria ORDER BY p.precio",nativeQuery = true)
    Set<Producto> findByCategoriaprodPrecioprod(@Param("categoria")String catgeoriaprod);
    @Query(value = "SELECT * FROM Producto p WHERE p.empresa_cif = :cif", nativeQuery = true)
    Set<Producto> findByEmpresaCif(@Param("cif")String cifemp);
    Set<Producto> findByNombreprod(String nombreprod);

}
