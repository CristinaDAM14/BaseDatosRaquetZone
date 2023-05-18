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
@Table(name = "Producto")
public class Producto {

    @Schema(description = "Id Identificador del Producto", example = "1", required = true)
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long idprod;

    @Schema(description = "Nombre del Producto", example = "Pelotas Head", required = true)
    @Column(name = "nombre", nullable = false)
    private String nombreprod;

    @Schema(description = "Categoria del Producto", example = "Pelotas", required = true)
    @Column(name = "categoria", nullable = false)
    private String categoriaprod;

    @Schema(description = "Precio del Producto", example = "12", required = true)
    @Column(name = "precio", nullable = false)
    private double precioprod;

    @Schema(description = "Iva del Producto", example = "21", required = true)
    @Column(name = "iva", nullable = false)
    private double ivaprod;

    @Schema(description = "Descuento del Producto", example = "5", required = true)
    @Column(name = "descuento", nullable = false)
    private double descuentoprod;

    @Schema(description = "Stock del Producto", example = "20", required = true)
    @Column(name = "stock", nullable = false)
    private int stockprod;

    @ManyToOne(fetch=FetchType.EAGER)
    private Empresa empresa;

    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Producto_compra> producto_compras = new ArrayList<>();

}
