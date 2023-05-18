package com.example.RaquetZone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Compra")
public class Compra {

    @Schema(description = "Id Identificador de la compra", example = "1", required = true)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private long idcomp;

    @Schema(description = "Fecha de la compra",example = "12/03/2022",required = true)
    @Column(name = "fecha", nullable = false)
    private Date fechacomp;

    @Schema(description = "Hora de la compra",example = "13:51:24",required = true)
    @Column(name = "hora", nullable = false)
    private Time horacomp;

    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "compra",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Producto_compra> producto_compras = new ArrayList<>();
}
