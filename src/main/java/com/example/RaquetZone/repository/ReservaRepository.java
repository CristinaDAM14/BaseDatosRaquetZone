package com.example.RaquetZone.repository;

import com.example.RaquetZone.domain.Reserva;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ReservaRepository extends CrudRepository<Reserva,Long> {
    Set<Reserva> findAll();
    Optional<Reserva> findByIdRes(long idRes);
    @Query(value = "SELECT * FROM Reserva r WHERE r.cliente_dni = :cliente_dni ORDER BY fecha, hora",nativeQuery = true)
    Set<Reserva> findByClienteDni(@Param("cliente_dni") String dnicli);
    @Query(value = "SELECT * FROM Reserva r WHERE r.servicio_id = :idServ", nativeQuery = true)
    Set<Reserva> findByServicioId(@Param("idServ")long idServ);
    @Query(value = "SELECT * FROM Reserva s WHERE s.fecha = :fecha",nativeQuery = true)
    Set<Reserva> findReservasByDay(@Param("fecha") Date fechares);
    @Query(value = "SELECT * FROM Reserva r WHERE r.fecha >= CURDATE()",nativeQuery = true)
    Set<Reserva> findReservasFromToday();
    Set<Reserva> findByUsuarioDniusrAndFechaRes(String dniusr, Date fechares);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Reserva WHERE cliente_dni = :cliente_dni",nativeQuery = true)
    void deleteByClienteDnicli(@Param("cliente_dni") String dnicli);
}
