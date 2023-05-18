package com.example.RaquetZone.repository;

import com.example.RaquetZone.domain.Compra;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CompraRepository extends CrudRepository<Compra,Long> {

    Set<Compra> findAll();
    Optional<Compra> findByIdcomp(long idcomp);
    Set<Compra> findByFechacomp(Date fechacomp);
}
