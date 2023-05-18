package com.example.RaquetZone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cliente")
public class Cliente {

    @Schema(description = "DNI Identificador del cliente", example = "12345678X", required = true)
    @Id
    @Column(name = "dni")
    private String dnicli;

    @Schema(description = "Nombre del cliente", example = "Carlos Alvarez", required = true)
    @Column(name = "nombre", nullable = false)
    private String nombrecli;

    @Schema(description = "Password del cliente", example = "1234", required = true)
    @Column(name = "password", nullable = false)
    private String passwordcli;

    @Schema(description = "Numero de horas de clase del cliente", example = "1", required = true)
    @Column(name = "num_horas", nullable = false)
    private int numhorascli;

    @Schema(description = "Telefono del cliente", example = "740156456", required = true)
    @Column(name = "telefono", nullable = false, unique = true)
    private String telefonocli;

    @Schema(description = "Email del cliente", example = "carlos@gmail.com", required = true)
    @Column(name = "email", nullable = false, unique = true)
    private String emailcli;

    @Schema(description = "Define si un cliente esta activo o ha borrado su cuenta", example = "true",defaultValue = "true", required = true)
    @Column(name = "activo", nullable = false)
    private boolean activo;

    @ManyToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Empresa> empresa = new HashSet<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Compra> compras= new ArrayList<>();

    @OneToMany(mappedBy = "cliente",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reserva> reservas = new ArrayList<>();

}


