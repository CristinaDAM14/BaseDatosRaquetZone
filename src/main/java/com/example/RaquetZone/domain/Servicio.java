package com.example.RaquetZone.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Servicio")
public class Servicio {

    @Schema(description = "id Identificador del servicio", example = "1", required = true)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long idserv;

    @Schema(description = "Descripcion del servicio", example = "Clase particular de tenis", required = true)
    @Column(name="descripcion",nullable = false)
    private String descripcionserv;

    @Schema(description = "unidades de tiempo en 30 min", example = "4", required = true)
    @Column(name="unidades_tiempo",nullable = false)
    private int unidadestiemposerv;

    @Schema(description = "Precio del servicio", example = "15.25", required = true)
    @Column(name="precio",nullable = false)
    private double precioserv;

    @Schema(description = "iva del precio del servicio", example = "21", required = true)
    @Column(name="iva",nullable = false)
    private double ivaserv;

    @Schema(description = "Descuento del servicio", example = "5", required = true)
    @Column(name="descuento",nullable = false)
    private double descuentoserv;

    @ManyToOne(fetch=FetchType.EAGER)
    private Empresa empresa;

    @OneToMany(mappedBy = "servicio",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reserva> reservas = new ArrayList<>();
}
