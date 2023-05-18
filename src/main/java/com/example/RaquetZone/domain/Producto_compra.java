package com.example.RaquetZone.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Producto_compra")
public class Producto_compra{

    @EmbeddedId
    private ProductoCompraID id;

    @ManyToOne(fetch=FetchType.EAGER)
    @MapsId("idProducto")
    private Producto producto;

    @ManyToOne(fetch=FetchType.EAGER)
    @MapsId("idCompra")
    private Compra compra;

    @Schema(description = "Cantidad de productos en las compras", example = "20", required = true)
    @Column(name = "cantidad", nullable = false)
    private int cantidadprodcomp;
}


@Data
@Embeddable
class ProductoCompraID implements Serializable{

    private long idProducto;

    private long idCompra;
}

