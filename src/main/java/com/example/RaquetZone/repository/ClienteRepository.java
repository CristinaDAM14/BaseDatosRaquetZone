package com.example.RaquetZone.repository;

import com.example.RaquetZone.domain.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, String>
{
    Set<Cliente> findAll();
    Set<Cliente> findByNombrecli(String nombrecli);
    Set<Cliente> findByTelefonocli(String telefonocli);
    Optional<Cliente> findByDnicli(String dnicli);
    Set<Cliente> findByEmailcli(String emailcli);
    Set<Cliente> findByDnicliAndPasswordcli(String dnicli, String passwordcli);
    Cliente findByEmailcliAndPasswordcli(String emailcli, String passwordcli);
    @Query(value = "SELECT dni,nombre,telefono,email,num_horas,password FROM Cliente c ORDER BY c.num_horas",nativeQuery = true)
    Set<Cliente> findByNumHorascli();
    Set<Cliente> findClientesByEmpresaCifemp(String cifemp);
}
