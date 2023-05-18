package com.example.RaquetZone.controller;

import com.example.RaquetZone.domain.Horario;
import com.example.RaquetZone.domain.Usuario;
import com.example.RaquetZone.exception.EmpresaNotFoundException;
import com.example.RaquetZone.exception.HorarioNotFoundException;
import com.example.RaquetZone.service.EmpresaService;
import com.example.RaquetZone.service.HorarioService;
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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.example.RaquetZone.controller.Response.NOT_FOUND;

@RestController
@Tag(name = "Horario", description = "Lista de horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EmpresaService empresaService;

    @Operation(summary = "Obtiene el listado de horarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de horarios",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Horario.class))))
    })
    @GetMapping(value = "/horarios", produces = "application/json")
    public ResponseEntity<Set<Horario>> getHorario(){
        Set<Horario> horarios = null;
        horarios = horarioService.findAll();
        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de horarios por fecha y empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de horarios por fecha",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Horario.class))))
    })
    @GetMapping(value = "/horarios/{fechahor}", produces = "application/json")
    public ResponseEntity<Set<Horario>> findByFechahor(@PathVariable Date fechahor){
        Set<Horario> horarios = horarioService.findByFechahor(fechahor);
        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de horarios por empleado de una empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de horarios por fecha",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Horario.class))))
    })
    @GetMapping(value = "/horarios/{cif}/{fecha}", produces = "application/json")
    public ResponseEntity<Set<Horario>> getEmpresaEmpleadosHorarios(@PathVariable String cif, @PathVariable Date fecha){

        if (empresaService.findByCifemp(cif).isEmpty()){
            throw new HorarioNotFoundException("La empresa no existe");
        }
        Set<Usuario> usuarios = usuarioService.findUsuariosByEmpresaCifempAndRolusr(cif,1);
        Set<Horario> horarios = new HashSet<>();
        for (Usuario usuario: usuarios) {
            horarios.addAll(horarioService.findByUsuarioDniusrAndFechahor(usuario.getDniusr(), fecha));
        }
        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo horario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el horario", content = @Content
                    (schema = @Schema(implementation = Horario.class)))
    })
    @PostMapping(value = "/horario/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Horario> addHorario(@RequestBody Horario horario){
         Optional<Usuario> profesor = usuarioService.findByDniusr(horario.getUsuario().getDniusr());
        if (profesor.get().getRolusr() != 1){
            throw new HorarioNotFoundException("Solo se pueden añadir profesores");
        }
        if (!horarioService.findByUsuarioDniusrAndFechahor(horario.getUsuario().getDniusr(),horario.getFechahor()).isEmpty()){
            throw new HorarioNotFoundException("Ese profesor ya tiene vacaciones ese dia");
        }
        Horario addedHorario = horarioService.addHorario(horario);
        return new ResponseEntity<>(addedHorario, HttpStatus.OK);
    }

    @Operation(summary = "Modifica un horario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica el horario", content = @Content
                    (schema = @Schema(implementation = Horario.class))),
            @ApiResponse(responseCode = "404", description = "El horario no existe", content = @Content
                    (schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value ="/horario/modify/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Horario> modifyHorario(@PathVariable long id, @RequestBody Horario newHorario){
        Horario horario = horarioService.modifyHorario(id, newHorario);
        return new ResponseEntity<>(horario, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un horario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el horario", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "El horario no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/horario/delete/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteHorario(@PathVariable long id){
        horarioService.deleteHorario(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(HorarioNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(HorarioNotFoundException pnfe) {
        Response response = Response.errorResonse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
