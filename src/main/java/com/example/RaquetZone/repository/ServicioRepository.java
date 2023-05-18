package com.example.RaquetZone.repository;

import com.example.RaquetZone.domain.Servicio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ServicioRepository extends CrudRepository<Servicio, Long> {

    Set<Servicio> findAll();
    Optional<Servicio> findByIdserv(long idserv);
    @Query(value = "SELECT * FROM Servicio s ORDER BY s.precio",nativeQuery = true)
    Set<Servicio> findByPrecioserv();
    @Query(value = "SELECT * FROM Servicio s ORDER BY s.unidades_tiempo",nativeQuery = true)
    Set<Servicio> findByUnidadestiemposerv();
    @Query(value = "SELECT * FROM Servicio s ORDER BY s.descuento",nativeQuery = true)
    Set<Servicio> findByDescuentoserv();
    @Query(value = "SELECT * FROM Servicio s WHERE s.empresa_cif = :cif", nativeQuery = true)
    Set<Servicio> findByEmpresaCif(@Param("cif")String cifemp);
}
