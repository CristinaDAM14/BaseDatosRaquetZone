package com.example.RaquetZone.controller;

import com.example.RaquetZone.domain.Compra;
import com.example.RaquetZone.domain.Producto;
import com.example.RaquetZone.exception.CompraNotFoundException;
import com.example.RaquetZone.exception.EmpresaNotFoundException;
import com.example.RaquetZone.service.CompraService;
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

import java.util.Date;
import java.util.Set;

import static com.example.RaquetZone.controller.Response.NOT_FOUND;

@RestController
@Tag(name = "Compra", description = "Lista de compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Operation(summary = "Obtiene el listado de compras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de compras",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Compra.class))))
    })
    @GetMapping(value = "/compras",produces = "application/json")
    public ResponseEntity<Set<Compra>> getCompras(){
        Set<Compra> compras = null;
        compras = compraService.findAll();
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un listado de compras por su fecha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existen las compras",
                    content = @Content(schema = @Schema(implementation =
                            Producto.class))),
            @ApiResponse(responseCode = "404", description = "No hay compras en esa fecha",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/compras/fecha",produces = "application/json")
    public ResponseEntity<Set<Compra>> getCompraByFecha(@RequestParam(value = "fecha", defaultValue = "") Date fecha){

        Set<Compra> compras = compraService.findByFechacomp(fecha);

        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @Operation(summary = "Registra una nueva compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra la compra", content = @Content
                    (schema = @Schema(implementation = Compra.class)))
    })
    @PostMapping(value = "/compra/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Compra> addCompra(@RequestBody Compra compra){
        Compra addedCompra = compraService.addCompra(compra);
        return new ResponseEntity<>(addedCompra, HttpStatus.OK);
    }

    @Operation(summary = "Modifica una compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica la compra", content = @Content
                    (schema = @Schema(implementation = Compra.class))),
            @ApiResponse(responseCode = "404", description = "La compra no existe", content = @Content
                    (schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value ="/compra/modify/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Compra> modifyCompra(@PathVariable long id, @RequestBody Compra newCompra){
        Compra compra = compraService.modifyCompra(id, newCompra);
        return new ResponseEntity<>(compra, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina la compra", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "La compra no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/compra/delete/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteCompra(@PathVariable long id){
        compraService.deleteCompra(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(CompraNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(CompraNotFoundException cnfe) {
        Response response = Response.errorResonse(NOT_FOUND, cnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
