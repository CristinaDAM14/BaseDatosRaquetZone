package com.example.RaquetZone.controller;

import com.example.RaquetZone.domain.Producto;
import com.example.RaquetZone.domain.Reserva;

import com.example.RaquetZone.domain.Usuario;
import com.example.RaquetZone.exception.ReservaNotFoundException;
import com.example.RaquetZone.service.ReservaService;
import com.example.RaquetZone.service.ServicioService;
import com.example.RaquetZone.service.UsuarioService;
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

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.RaquetZone.controller.Response.NOT_FOUND;

@RestController
@Tag(name = "Reserva",description = "Lista de Reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ServicioService servicioService;

    @Operation(summary = "Obtiene el listado de Reservas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de Reservas",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Reserva.class))))
    })
    @GetMapping(value = "/reservas", produces = "application/json")
    public ResponseEntity<Set<Reserva>> getReservas(){
        Set<Reserva> reservas = null;
        reservas = reservaService.findAll();
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene las reservas de un cliente ordenadas por fecha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la reserva",
                    content = @Content(schema = @Schema(implementation =
                            Reserva.class))),
            @ApiResponse(responseCode = "404", description = "La reserva no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/reserva/{dnicli}",produces = "application/json")
    public ResponseEntity<Set<Reserva>> getReservaByClienteDni(@PathVariable String dnicli){

        Set<Reserva> reservas = reservaService.findByClienteDni(dnicli);

        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una reserva de la fecha del dia en adelante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la reserva",
                    content = @Content(schema = @Schema(implementation =
                            Producto.class))),
            @ApiResponse(responseCode = "404", description = "La reserva no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/reserva/fecha",produces = "application/json")
    public ResponseEntity<Set<Reserva>> findReservasByDay(@RequestParam(value = "fecha", defaultValue = "") Date fechares){

        Set<Reserva> reservas = reservaService.findReservasByDay(fechares);

        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene las reservas de hoy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la reserva",
                    content = @Content(schema = @Schema(implementation =
                            Producto.class))),
            @ApiResponse(responseCode = "404", description = "La reserva no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/reserva/fromtoday",produces = "application/json")
    public ResponseEntity<Set<Reserva>> findReservasFromToday(){

        Set<Reserva> reservas = reservaService.findReservasFromToday();

        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @Operation(summary = "Añade una reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva añadida correctamente",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Reserva.class))))
    })
    @PostMapping(value = "/reserva/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Reserva> addReserva(@RequestBody Reserva reserva){
        Optional<Usuario> profesor = usuarioService.findByDniusr(reserva.getUsuario().getDniusr());
        if(profesor.isEmpty()){
            throw new ReservaNotFoundException("No existe ese profesor");
        }
        if(servicioService.findByIdserv(reserva.getServicio().getIdserv()).isEmpty()){
            throw new ReservaNotFoundException("No existe un servicio con ese id");
        }
        if (profesor.get().getRolusr() != 1){
            throw new ReservaNotFoundException("Solo se pueden añadir profesores en la reserva");
        }
        List<Reserva> reservas = profesor.get().getReservas();
        long milSecReserva = reserva.getServicio().getUnidadestiemposerv()* 1800000L;
        for (Reserva reservaProf : reservas) {
            //TODO: añadir que la hora de reserva dependa de lo que dura el servicio y no 1h
            Time horaResProf = reservaProf.getHoraRes();
            long milSecReservaProf = reservaProf.getServicio().getUnidadestiemposerv()* 1800000L;
            if (reservaProf.getFechaRes().toString().equals(reserva.getFechaRes().toString())
                    && horaResProf.after(new Time(reserva.getHoraRes().getTime()-3600000))
                    && horaResProf.before(new Time(reserva.getHoraRes().getTime()+3600000))) {
                throw new ReservaNotFoundException("Este profesor ya tiene reservada esta hora");
            }
        }
        Reserva addedReserva = reservaService.addReserva(reserva);
        return new ResponseEntity<>(addedReserva, HttpStatus.OK);
    }

    @Operation(summary = "Modifica una reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica la reserva", content = @Content
                    (schema = @Schema(implementation = Reserva.class))),
            @ApiResponse(responseCode = "404", description = "La reserva no existe", content = @Content
                    (schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value ="/reserva/modify/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Reserva> modifyReserva(@PathVariable long id, @RequestBody Reserva newReserva){
        Reserva reserva = reservaService.modifyReserva(id, newReserva);
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina la reserva", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "La reserva no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/reserva/delete/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteReserva(@PathVariable long id){
        reservaService.deleteReserva(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(ReservaNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(ReservaNotFoundException pnfe) {
        Response response = Response.errorResonse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
