package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Horario;
import com.example.RaquetZone.domain.Reserva;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;
import java.util.Set;

@Service
public interface ReservaService {
    Set<Reserva> findAll();
    Optional<Reserva> findByIdRes(long idRes);
    Set<Reserva> findByServicioId(long idServ);
    Set<Reserva> findReservasFromToday();
    Set<Reserva> findByUsuarioDniusrAndFechaRes(String dniusr, Date fechares);
    Set<Reserva> findByClienteDni(String dnicli);
    Set<Reserva> findReservasByDay(Date fechares);
    Reserva addReserva(Reserva reserva);
    Reserva modifyReserva(long idRes, Reserva newReserva);
    void deleteReserva(long idRes);
    void deleteReservasByClienteDni(String dnicli);
}
