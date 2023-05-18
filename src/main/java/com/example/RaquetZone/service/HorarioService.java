package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Horario;
import com.example.RaquetZone.domain.Usuario;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Set;

@Service
public interface HorarioService {

    Set<Horario> findAll();
    Set<Horario> findByFechahor(Date fechahor);
    Set<Horario> findByUsuarioDniusrAndFechahor(String dniusr, Date fechahor);
    Set<Horario> findByUsuarioDniusr(String dniusr);
    Horario addHorario(Horario horario);
    Horario modifyHorario(long idhor, Horario newHorario);
    void deleteHorario(long idhor);
}
