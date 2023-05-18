package com.example.RaquetZone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Schema(description = "DNI Identificador del usuario", example = "12345678X", required = true)
    @Id
    @Column(name="dni")
    private String dniusr;

    @Schema(description = "Nombre del usuario", example = "Carlos Alvarez", required = true)
    @Column(name="nombre",nullable = false)
    private String nombreusr;

    @Schema(description = "Password del usuario", example = "1234", required = true)
    @Column(name="password",nullable = false)
    private String passwordusr;

    @Schema(description = "Rol del usuario", example = "1", required = true)
    @Column(name="rol",nullable = false)
    private int rolusr;

    @Schema(description = "Telefono del usuario", example = "740156456", required = true)
    @Column(name="telefono",nullable = false)
    private String telefonousr;

    @Schema(description = "Email del usuario", example = "carlos@gmail.com", required = true)
    @Column(name="email",nullable = false)
    private String emailusr;

    @Schema(description = "Direccion de la vivienda del usuario", example = "Calle Ramon y Cajal nº45", required = true)
    @Column(name="direccion",nullable = false)
    private String direccionusr;

    @Schema(description = "Define si un usuario esta activo o ya no trabaja", example = "true",defaultValue = "true", required = true)
    @Column(name = "activo", nullable = false)
    private boolean activo;

    @ManyToMany(mappedBy = "usuario")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Empresa>empresa = new HashSet<>();

    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Horario> horarios = new ArrayList<>();

    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reserva> reservas = new ArrayList<>();

}
