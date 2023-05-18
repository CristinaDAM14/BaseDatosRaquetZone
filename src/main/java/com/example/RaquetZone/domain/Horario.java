package com.example.RaquetZone.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Horario")
public class Horario {

    @Schema(description = "Identificador de la reserva", example = "1", required = true)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private long idhor;

    @Schema(description = "Fecha",example = "12/12/2002",required = true)
    @Column(name = "fecha", nullable = false)
    private Date fechahor;

    @ManyToOne(fetch=FetchType.EAGER,optional = false)
    private Usuario usuario;

}
