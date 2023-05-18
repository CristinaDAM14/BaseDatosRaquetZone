package com.example.RaquetZone.controller;

import com.example.RaquetZone.domain.Producto;
import com.example.RaquetZone.exception.EmpresaNotFoundException;
import com.example.RaquetZone.exception.ProductoNotFoundException;
import com.example.RaquetZone.service.ProductoService;
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

import java.util.Set;

import static com.example.RaquetZone.controller.Response.NOT_FOUND;

@RestController
@Tag(name = "Producto", description = "Lista de productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Obtiene el listado de productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de productos",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Producto.class))))
    })
    @GetMapping(value = "/productos",produces = "application/json")
    public ResponseEntity<Set<Producto>> getProductos(){
        Set<Producto> productos = null;
        productos = productoService.findAll();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un producto determinado por su categoria y stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el producto",
                    content = @Content(schema = @Schema(implementation =
                            Producto.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/producto/categoriastock",produces = "application/json")
    public ResponseEntity<Set<Producto>> getProductoByCategoriaprodStockprod(@RequestParam(value = "categoria", defaultValue = "") String categoriaprod){

        Set<Producto> productos = productoService.findByCategoriaprodStockprod(categoriaprod);

        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un producto determinado por su categoria y precio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el producto",
                    content = @Content(schema = @Schema(implementation =
                            Producto.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/producto/categoriaprecio",produces = "application/json")
    public ResponseEntity<Set<Producto>> getProductoByCategoriaprodPrecioprod(@RequestParam(value = "categoria", defaultValue = "") String categoriaprod){

        Set<Producto> productos = productoService.findByCategoriaprodPrecioprod(categoriaprod);

        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un producto determinado por su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el producto",
                    content = @Content(schema = @Schema(implementation =
                            Producto.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/producto/{nombreprod}",produces = "application/json")
    public ResponseEntity<Set<Producto>> getProductoByNombreprod(@PathVariable String nombreprod){

        Set<Producto> productos = productoService.findByNombreprod(nombreprod);

        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el producto", content = @Content
                    (schema = @Schema(implementation = Producto.class)))
    })
    @PostMapping(value = "/producto/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Producto> addProducto(@RequestBody Producto producto){
        Producto addedProducto = productoService.addProducto(producto);
        return new ResponseEntity<>(addedProducto, HttpStatus.OK);
    }

    @Operation(summary = "Modifica un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica el producto", content = @Content
                    (schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe", content = @Content
                    (schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value ="/producto/modify/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Producto> modifyProducto(@PathVariable long id, @RequestBody Producto newProducto){
        Producto producto = productoService.modifyProducto(id, newProducto);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el producto", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/producto/delete/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteProducto(@PathVariable long id){
        productoService.deleteProducto(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(ProductoNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(ProductoNotFoundException pnfe) {
        Response response = Response.errorResonse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
