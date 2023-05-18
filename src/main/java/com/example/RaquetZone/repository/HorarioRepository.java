package com.example.RaquetZone.repository;

import com.example.RaquetZone.domain.Horario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Set;

@Repository
public interface HorarioRepository extends CrudRepository<Horario,Long> {

    Set<Horario> findAll();
    Set<Horario> findByFechahor(Date fechahor);
    Set<Horario> findByUsuarioDniusrAndFechahor(String dniusr, Date fechahor);
    Set<Horario> findByUsuarioDniusr(String dniusr);
}
