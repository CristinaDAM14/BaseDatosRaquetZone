package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Horario;
import com.example.RaquetZone.exception.HorarioNotFoundException;
import com.example.RaquetZone.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Set;

@Service
public class HorarioServiceImpl implements HorarioService{

    @Autowired
    private HorarioRepository horarioRepository;
    @Override
    public Set<Horario> findAll() {
        return horarioRepository.findAll();
    }

    @Override
    public Set<Horario> findByFechahor(Date fechahor) {
        return horarioRepository.findByFechahor(fechahor);
    }

    @Override
    public Set<Horario> findByUsuarioDniusrAndFechahor(String dniusr, Date fechahor) {
        return horarioRepository.findByUsuarioDniusrAndFechahor(dniusr,fechahor);
    }

    @Override
    public Set<Horario> findByUsuarioDniusr(String dniusr) {
        return horarioRepository.findByUsuarioDniusr(dniusr);
    }

    @Override
    public Horario addHorario(Horario horario) {
        return horarioRepository.save(horario);
    }

    @Override
    public Horario modifyHorario(long idhor, Horario newHorario) {
        Horario horario = horarioRepository.findById(idhor).orElseThrow(() -> new HorarioNotFoundException(idhor));
        newHorario.setFechahor(horario.getFechahor());
        newHorario.setUsuario(horario.getUsuario());
        return horarioRepository.save(newHorario);}

    @Override
    public void deleteHorario(long idhor) {
        horarioRepository.findById(idhor).orElseThrow(() -> new HorarioNotFoundException(idhor));
        horarioRepository.deleteById(idhor);
    }
}
