package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Horario;
import com.example.RaquetZone.domain.Producto;
import com.example.RaquetZone.domain.Reserva;
import com.example.RaquetZone.domain.Servicio;
import com.example.RaquetZone.exception.ReservaNotFoundException;
import com.example.RaquetZone.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class ReservaServiceImpl implements ReservaService{

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public Set<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Override
    public Optional<Reserva> findByIdRes(long idRes){
        return reservaRepository.findByIdRes(idRes);
    }

    @Override
    public Set<Reserva> findByServicioId(long idServ) {
        return reservaRepository.findByServicioId(idServ);
    }

    @Override
    public Set<Reserva> findReservasFromToday() {
        return reservaRepository.findReservasFromToday();
    }

    @Override
    public Set<Reserva> findByUsuarioDniusrAndFechaRes(String dniusr, Date fechares) {
        return reservaRepository.findByUsuarioDniusrAndFechaRes(dniusr, fechares);
    }

    @Override
    public Set<Reserva> findByClienteDni(String dnicli) {
        return reservaRepository.findByClienteDni(dnicli);
    }

    @Override
    public Set<Reserva> findReservasByDay(Date fechares) {
        return reservaRepository.findReservasByDay(fechares);
    }

    @Override
    public Reserva addReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public Reserva modifyReserva(long idRes, Reserva newReserva) {
        Reserva reserva = reservaRepository.findByIdRes(idRes).orElseThrow(() -> new ReservaNotFoundException(idRes));
        newReserva.setIdRes(reserva.getIdRes());
        return reservaRepository.save(newReserva);
    }

    @Override
    public void deleteReserva(long idRes) {
        reservaRepository.findByIdRes(idRes).orElseThrow(() -> new ReservaNotFoundException(idRes));
        reservaRepository.deleteById(idRes);
    }

    @Override
    public void deleteReservasByClienteDni(String dnicli) {
        reservaRepository.findByClienteDni(dnicli);
        reservaRepository.deleteByClienteDnicli(dnicli);
    }
}
