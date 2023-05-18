package com.example.RaquetZone.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Reserva")
public class Reserva implements Serializable{

    @Schema(description = "Id Identificador de la Reserva", example = "1", required = true)
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long idRes;

    @Schema(description = "Numero de pista", example = "1", required = true)
    @Column(name = "pista", nullable = false)
    private int numPista;

    @Schema(description = "Fecha en la que se tiene la pista", example = "1", required = true)
    @Column(name = "fecha", nullable = false)
    private Date fechaRes;

    @Schema(description = "Hora a la que se tiene la pista", example = "1", required = true)
    @Column(name = "hora", nullable = false)
    private Time horaRes;

    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    @ToString.Exclude
    private Cliente cliente;
    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    @ToString.Exclude
    private Servicio servicio;
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @ToString.Exclude
    private Usuario usuario;
}