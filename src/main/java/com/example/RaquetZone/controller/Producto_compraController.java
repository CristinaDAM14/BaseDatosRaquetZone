package com.example.RaquetZone.controller;

import com.example.RaquetZone.domain.Horario;
import com.example.RaquetZone.domain.Producto_compra;
import com.example.RaquetZone.domain.Usuario;
import com.example.RaquetZone.exception.HorarioNotFoundException;
import com.example.RaquetZone.exception.Producto_CompraNotFoundException;
import com.example.RaquetZone.service.Producto_compraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@Tag(name = "Producto_compra",description = "Lista de Producto_compra")
public class Producto_compraController {

    @Autowired
    Producto_compraService producto_compraService;

    @Operation(summary = "Obtiene el listado de Productos_compras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de Productos_compras",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Producto_compra.class))))
    })
    @GetMapping(value = "/producto_compras", produces = "application/json")
    public ResponseEntity<Set<Producto_compra>> getProducto_compras(){
        Set<Producto_compra> producto_compras = null;
        producto_compras = producto_compraService.findAll();
        return new ResponseEntity<>(producto_compras, HttpStatus.OK);
    }

    @Operation(summary = "Registra un producto en una compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registró el producto en la compra", content = @Content
                    (schema = @Schema(implementation = Producto_compra.class)))
    })
    @PostMapping(value = "/producto_compra/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Producto_compra> addProducto_Compra(@RequestBody Producto_compra producto_compra){
        producto_compraService.addProductoCompra(producto_compra);
        return new ResponseEntity<>(producto_compra, HttpStatus.OK);
    }
}
