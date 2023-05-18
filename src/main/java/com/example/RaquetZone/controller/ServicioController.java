package com.example.RaquetZone.controller;

import com.example.RaquetZone.domain.Reserva;
import com.example.RaquetZone.domain.Servicio;
import com.example.RaquetZone.exception.EmpresaNotFoundException;
import com.example.RaquetZone.exception.ServicioNotFoundException;
import com.example.RaquetZone.service.ReservaService;
import com.example.RaquetZone.service.ServicioService;
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

import java.util.List;
import java.util.Set;

import static com.example.RaquetZone.controller.Response.NOT_FOUND;

@RestController
@Tag(name = "Servicio", description = "Lista de servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;
    @Autowired
    private ReservaService reservaService;


    @Operation(summary = "Obtiene el listado de servicios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de servicios",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Servicio.class))))
    })
    @GetMapping(value = "/servicios",produces = "application/json")
    public ResponseEntity<Set<Servicio>> getServicios(){
        Set<Servicio> servicios = null;
        servicios = servicioService.findAll();
        return new ResponseEntity<>(servicios, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de servicios oredenados por precio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de servicios oredenados por precio",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Servicio.class))))
    })
    @GetMapping(value = "/servicios/precio",produces = "application/json")
    public ResponseEntity<Set<Servicio>> getServiciosPrecio(){
        Set<Servicio> servicios = null;
        servicios = servicioService.findByPrecioserv();
        return new ResponseEntity<>(servicios, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de servicios oredenados por unidades de tiempo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de servicios oredenados por unidades de tiempo",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Servicio.class))))
    })
    @GetMapping(value = "/servicios/unidadestiempo",produces = "application/json")
    public ResponseEntity<Set<Servicio>> getServiciosUnidadestiempo(){
        Set<Servicio> servicios = null;
        servicios = servicioService.findByUnidadestiemposerv();
        return new ResponseEntity<>(servicios, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de servicios oredenados por descuento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de servicios oredenados por descuento",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Servicio.class))))
    })
    @GetMapping(value = "/servicios/descuento",produces = "application/json")
    public ResponseEntity<Set<Servicio>> getServiciosDescuento(){
        Set<Servicio> servicios = null;
        servicios = servicioService.findByDescuentoserv();
        return new ResponseEntity<>(servicios, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene las reservas de una servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el servicio",
                    content = @Content(schema = @Schema(implementation =
                            Reserva.class))),
            @ApiResponse(responseCode = "404", description = "El servicio no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/servicio/{id}/reservas", produces = "application/json")
    public ResponseEntity<Set<Reserva>> getReservasServicio(@PathVariable long id){
        if (servicioService.findByIdserv(id).isEmpty()){
            throw new EmpresaNotFoundException("La empresa no existe");
        }
        Set<Reserva> reservas = reservaService.findByServicioId(id);
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el servicio", content = @Content
                    (schema = @Schema(implementation = Servicio.class)))
    })
    @PostMapping(value = "/servicio/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Servicio> addServicio(@RequestBody Servicio servicio){
        Servicio addedServicio = servicioService.addServicio(servicio);
        return new ResponseEntity<>(addedServicio, HttpStatus.OK);
    }


    @Operation(summary = "Modifica un servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica el servicio", content = @Content
                    (schema = @Schema(implementation = Servicio.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe", content = @Content
                    (schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value ="/servicio/modify/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Servicio> modifyServicio(@PathVariable long id, @RequestBody Servicio newServicio){
        Servicio servicio = servicioService.modifyServicio(id, newServicio);
        return new ResponseEntity<>(servicio, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el servicio", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/servicio/delete/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteServicio(@PathVariable long id){
        servicioService.deleteServicio(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(ServicioNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(ServicioNotFoundException pnfe) {
        Response response = Response.errorResonse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
