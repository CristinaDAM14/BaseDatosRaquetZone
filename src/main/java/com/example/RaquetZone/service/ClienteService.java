package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Cliente;
import com.example.RaquetZone.domain.Empresa;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface ClienteService {

    Set<Cliente> findAll();
    Set<Cliente> findClientesByEmpresaCifemp(String cifemp);
    Set<Cliente> findByNombrecli(String nombrecli);
    Optional<Cliente> findByDnicli(String dnicli);
    Set<Cliente> findByEmailcli(String emailcli);
    Set<Cliente> findByDnicliAndPasswordcli(String dnicli, String passwordcli);
    Cliente findByEmailAndPassword(String dni, String passwordcli);
    Set<Cliente> findByNumHorascli();
    Set<Cliente> findByTelefonocli(String telefonocli);
    Cliente addCliente(Cliente cliente);
    Cliente modifyCliente(String dnicli, Cliente newCliente);
    void deleteCliente(String dnicli);
}
