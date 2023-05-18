package com.example.RaquetZone.controller;

import com.example.RaquetZone.domain.*;
import com.example.RaquetZone.exception.EmpresaNotFoundException;
import com.example.RaquetZone.exception.ReservaNotFoundException;
import com.example.RaquetZone.exception.ServicioNotFoundException;
import com.example.RaquetZone.service.*;
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

import java.util.*;

import static com.example.RaquetZone.controller.Response.NOT_FOUND;


@RestController
@Tag(name = "Empresa", description = "Lista de empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private ServicioService servicioService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ReservaService reservaService;

    @Operation(summary = "Obtiene el listado de empresas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de empresas",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Empresa.class))))
    })
    @GetMapping(value = "/empresas", produces = "application/json")
    public ResponseEntity<Set<Empresa>> getEmpresas(){
        Set<Empresa> empresas = null;
                empresas = empresaService.findAll();
        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una empresa determinada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la empresa",
                    content = @Content(schema = @Schema(implementation =
                            Empresa.class))),
            @ApiResponse(responseCode = "404", description = "La empresa no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/empresa/{cif}", produces = "application/json")
    public ResponseEntity<Optional<Empresa>> getEmpresa(@PathVariable String cif){
        Optional<Empresa> empresa = empresaService.findByCifemp(cif);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene los servicios de una empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la empresa",
                    content = @Content(schema = @Schema(implementation =
                            Empresa.class))),
            @ApiResponse(responseCode = "404", description = "La empresa no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/empresa/{cif}/servicios", produces = "application/json")
    public ResponseEntity<Set<Servicio>> getEmpresaServicios(@PathVariable String cif){
        if (empresaService.findByCifemp(cif).isEmpty()){
            throw new EmpresaNotFoundException("La empresa no existe");
        }
        Set<Servicio> servicios = servicioService.findByEmpresaCif(cif);
        return new ResponseEntity<>(servicios, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene los productos de una empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la empresa",
                    content = @Content(schema = @Schema(implementation =
                            Empresa.class))),
            @ApiResponse(responseCode = "404", description = "La empresa no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/empresa/{cif}/productos", produces = "application/json")
    public ResponseEntity<Set<Producto>> getEmpresaProductos(@PathVariable String cif){
        if (empresaService.findByCifemp(cif).isEmpty()){
            throw new EmpresaNotFoundException("La empresa no existe");
        }
        Set<Producto> productos = productoService.findByEmpresaCif(cif);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene los clientes de una empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la empresa",
                    content = @Content(schema = @Schema(implementation =
                            Empresa.class))),
            @ApiResponse(responseCode = "404", description = "La empresa no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/empresa/{cif}/clientes", produces = "application/json")
    public ResponseEntity<Set<Cliente>> getEmpresaClientes(@PathVariable String cif){

        if (empresaService.findByCifemp(cif).isEmpty()){
            throw new EmpresaNotFoundException("La empresa no existe");
        }
        Set<Cliente> clientes = clienteService.findClientesByEmpresaCifemp(cif);

        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene las reservas de una empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la empresa",
                    content = @Content(schema = @Schema(implementation =
                            Empresa.class))),
            @ApiResponse(responseCode = "404", description = "La empresa no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/empresa/{cif}/reservas", produces = "application/json")
    public ResponseEntity<Set<Reserva>> getEmpresaReservas(@PathVariable String cif){
        if (empresaService.findByCifemp(cif).isEmpty()){
            throw new EmpresaNotFoundException("La empresa no existe");
        }

        Set<Reserva> reservas = new HashSet<>();
        Set<Servicio> servicios = servicioService.findByEmpresaCif(cif);
        if (servicios.isEmpty()){
            throw new ServicioNotFoundException("No hay servicios en la empresa");
        }
        for (Servicio servicio : servicios) {
            reservas.addAll(reservaService.findByServicioId(servicio.getIdserv()));
        }
        if (reservas.isEmpty()){
            throw new ReservaNotFoundException("No hay reservas");
        }
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene los empleados de una empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la empresa",
                    content = @Content(schema = @Schema(implementation =
                            Empresa.class))),
            @ApiResponse(responseCode = "404", description = "La empresa no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/empresa/{cif}/empleados", produces = "application/json")
    public ResponseEntity<Set<Usuario>> getEmpresaEmpleados(@PathVariable String cif){

        if (empresaService.findByCifemp(cif).isEmpty()){
            throw new EmpresaNotFoundException("La empresa no existe");
        }
        Set<Usuario> usuarios = usuarioService.findUsuariosByEmpresaCifempAndRolusr(cif,1);

        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    //INCLUIR EN LA BASE DE DATOS
    @Operation(summary = "Obtiene los propietarios de una empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la empresa",
                    content = @Content(schema = @Schema(implementation =
                            Empresa.class))),
            @ApiResponse(responseCode = "404", description = "La empresa no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/empresa/{cif}/propietarios", produces = "application/json")
    public ResponseEntity<Set<Usuario>> getEmpresaPropietarios(@PathVariable String cif){

        if (empresaService.findByCifemp(cif).isEmpty()){
            throw new EmpresaNotFoundException("La empresa no existe");
        }
        Set<Usuario> usuarios = usuarioService.findUsuariosByEmpresaCifempAndRolusr(cif,2);

        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una empresa determinada por su nombre y email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la empresa",
                    content = @Content(schema = @Schema(implementation =
                            Empresa.class))),
            @ApiResponse(responseCode = "404", description = "La empresa no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
   @GetMapping(value = "/empresa/NomEmail", produces = "application/json")
    public ResponseEntity<Set<Empresa>> findByNomempAndEmailemp(@RequestParam Map<String,String> allParams){
        Set<Empresa> empresas = null;
        if(allParams.containsKey("nombre social")){
            String nomemp = allParams.get("nombre social");
            if(nomemp.equals(""))
                empresas = empresaService.findAll();
            else
                empresas = empresaService.findByNomemp(nomemp);
        }else if(allParams.containsKey("email")){
            String emailemp = allParams.get("email");
            if(emailemp.equals(""))
                empresas = empresaService.findAll();
            else
                empresas = empresaService.findByEmailemp(emailemp);
        }
        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }


    @Operation(summary = "Registra una nueva empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra la empresa", content = @Content
                    (schema = @Schema(implementation = Empresa.class)))
    })
    @PostMapping(value = "/empresa/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Empresa> addEmpresa(@RequestBody Empresa empresa){
        Empresa addedEmpresa = empresaService.addEmpresa(empresa);
        return new ResponseEntity<>(addedEmpresa, HttpStatus.OK);
    }

    @Operation(summary = "Modifica una empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica la empresa", content = @Content
                    (schema = @Schema(implementation = Empresa.class))),
            @ApiResponse(responseCode = "404", description = "La empresa no existe", content = @Content
                    (schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value ="/empresa/modify/{cif}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Empresa> modifyEmpresa(@PathVariable String cif, @RequestBody Empresa newEmpresa){
        Empresa empresa = empresaService.modifyEmpresa(cif, newEmpresa);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el producto", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/empresa/delete/{cif}", produces = "application/json")
    public ResponseEntity<Response> deleteEmpresa(@PathVariable String cif){
        empresaService.deleteByCifemp(cif);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(EmpresaNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(EmpresaNotFoundException pnfe) {
        Response response = Response.errorResonse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
