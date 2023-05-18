package com.example.RaquetZone.service;

import com.example.RaquetZone.domain.Cliente;
import com.example.RaquetZone.domain.Empresa;
import com.example.RaquetZone.exception.ClienteNotFoundException;
import com.example.RaquetZone.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EmpresaService empresaService;

    @Override
    public Set<Cliente> findAll(){return clienteRepository.findAll();}

    @Override
    public Set<Cliente> findClientesByEmpresaCifemp(String cifemp) {
        return clienteRepository.findClientesByEmpresaCifemp(cifemp);
    }

    @Override
    public Set<Cliente> findByNombrecli(String nombrecli) {
        return clienteRepository.findByNombrecli(nombrecli);
    }

    @Override
    public Optional<Cliente> findByDnicli(String dnicli) {
        return  clienteRepository.findByDnicli(dnicli);
    }

    @Override
    public Set<Cliente> findByEmailcli(String emailcli) {
        return clienteRepository.findByEmailcli(emailcli);
    }


    @Override
    public Set<Cliente> findByDnicliAndPasswordcli(String dnicli, String passwordcli) {
        return clienteRepository.findByDnicliAndPasswordcli(dnicli,passwordcli);
    }

    @Override
    public Cliente findByEmailAndPassword(String email, String password) {
        return clienteRepository.findByEmailcliAndPasswordcli(email, password);
    }

    @Override
    public Set<Cliente> findByNumHorascli() {
        return clienteRepository.findByNumHorascli();
    }

    @Override
    public Set<Cliente> findByTelefonocli(String telefonocli) {
        return clienteRepository.findByTelefonocli(telefonocli);
    }

    @Override
    public Cliente addCliente(Cliente newcliente) {
        return clienteRepository.save(newcliente);

    }

    @Override
    public Cliente modifyCliente(String dnicli, Cliente newCliente) {
        Cliente cliente = clienteRepository.findByDnicli(dnicli).orElseThrow(() -> new ClienteNotFoundException(dnicli));
        newCliente.setDnicli(cliente.getDnicli());
        return clienteRepository.save(newCliente);
    }

    @Override
    public void deleteCliente(String dnicli) {
        clienteRepository.deleteById(dnicli);
    }
}
