package com.example.RaquetZone.controller;

import com.example.RaquetZone.domain.Cliente;
import com.example.RaquetZone.domain.Empresa;
import com.example.RaquetZone.exception.ClienteNotFoundException;
import com.example.RaquetZone.exception.EmpresaNotFoundException;
import com.example.RaquetZone.service.ClienteService;
import com.example.RaquetZone.service.EmpresaService;
import com.example.RaquetZone.service.ReservaService;
import com.google.common.hash.Hashing;
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

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.example.RaquetZone.controller.Response.NOT_FOUND;

@RestController
@Tag(name = "Cliente", description = "Lista de clientes")
public class ClienteController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ReservaService reservaService;


    @Operation(summary = "Obtiene el listado de clientes de todas las empresas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de clientes",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Cliente.class))))
    })
    @GetMapping(value = "/clientes",produces = "application/json")
    public ResponseEntity<Set<Cliente>> getClientes(){
        Set<Cliente> clientes = null;
        clientes = clienteService.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un cliente determinado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el cliente",
                    content = @Content(schema = @Schema(implementation =
                            Empresa.class))),
            @ApiResponse(responseCode = "404", description = "El cliente no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/cliente/{dni}", produces = "application/json")
    public ResponseEntity<Optional<Cliente>> getCliente(@PathVariable String dni){
        Optional<Cliente> cliente = clienteService.findByDnicli(dni);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @Operation(summary = "Comprueba el login de un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el cliente",
                    content = @Content(schema = @Schema(implementation =
                            Empresa.class))),
            @ApiResponse(responseCode = "404", description = "El cliente no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @PostMapping(value = "/cliente/login",produces = "application/json")
    public ResponseEntity<Cliente> getClienteByNombrecliAndPasswordcli(@RequestParam(value = "email", defaultValue = "") String email,
                                                                             @RequestParam(value = "password", defaultValue = "") String password){
        String sha256hex = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        if(clienteService.findByEmailcli(email).isEmpty()){
            throw new ClienteNotFoundException("Ese email no esta registrado");
        }
        Cliente cliente = clienteService.findByEmailAndPassword(email,sha256hex);
        if(cliente == null){
            throw new ClienteNotFoundException("Credenciales incorrectas");
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de clientes por su nombre, email o teléfono")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de clientes",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Cliente.class))))
    })
    @GetMapping(value = "/cliente/NomEmailTel",produces = "application/json")
    public ResponseEntity<Set<Cliente>> getClienteByNombreEmailTelefono(@RequestParam Map<String,String> allParams){
        Set<Cliente> clientes = null;
        if(allParams.containsKey("nombre")){
            String nombreCliente = allParams.get("nombre");
            if(nombreCliente.equals(""))
                clientes = clienteService.findAll();
            else
                clientes = clienteService.findByNombrecli(nombreCliente);
        }else if(allParams.containsKey("email")){
            String emailCliente = allParams.get("email");
            if(emailCliente.equals(""))
                clientes = clienteService.findAll();
            else
                clientes = clienteService.findByEmailcli(emailCliente);

        }else if(allParams.containsKey("telefono")) {
            String telefonoCliente = allParams.get("telefono");
            if (telefonoCliente.equals(""))
                clientes = clienteService.findAll();
            else
                clientes = clienteService.findByTelefonocli(telefonoCliente);
        }else{
            clientes = clienteService.findAll();
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de clientes por numero de horas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de clientes por numero de horas",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Cliente.class))))
    })
    @GetMapping(value = "/cliente/numHoras",produces = "application/json")
    public ResponseEntity<Set<Cliente>> getClientesNumHoras(){
        Set<Cliente> clientes = null;
        clientes = clienteService.findByNumHorascli();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el cliente", content = @Content
                    (schema = @Schema(implementation = Empresa.class)))
    })
    @PostMapping(value = "/cliente/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Cliente> addCliente(@RequestBody Cliente cliente){
        String sha256hex = Hashing.sha256()
                .hashString(cliente.getPasswordcli(), StandardCharsets.UTF_8)
                .toString();
        cliente.setPasswordcli(sha256hex);
        Cliente addedCliente = clienteService.addCliente(cliente);
        return new ResponseEntity<>(addedCliente, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo cliente y lo relaciona con una empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el cliente", content = @Content
                    (schema = @Schema(implementation = Cliente.class)))
    })
    @PostMapping(value = "/empresa/{cif}/cliente", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Cliente> addClienteAEmpresa(@PathVariable(value = "cif") String cifemp, @RequestBody Cliente addCliente){
        Cliente cliente = empresaService.findByCifemp(cifemp).map(empresa -> {
            String sha256hex = Hashing.sha256()
                    .hashString(addCliente.getPasswordcli(), StandardCharsets.UTF_8)
                    .toString();
            addCliente.setPasswordcli(sha256hex);
            empresa.addCliente(addCliente);
            return clienteService.addCliente(addCliente);
        }).orElseThrow(() -> new ClienteNotFoundException("Cliente mal insertado"));

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @Operation(summary = "Modifica un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica el cliente", content = @Content
                    (schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "404", description = "El cliente no existe", content = @Content
                    (schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value ="/cliente/modify/{dni}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Cliente> modifyCliente(@PathVariable String dni, @RequestBody Cliente newCliente){
        String sha2456hex = Hashing.sha256()
                .hashString(newCliente.getPasswordcli(),StandardCharsets.UTF_8)
                .toString();
        newCliente.setPasswordcli(sha2456hex);
        Cliente cliente = clienteService.modifyCliente(dni, newCliente);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    /*@Operation(summary = "Elimina un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el cliente", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "El cliente no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/cliente/delete/{dni}", produces = "application/json")
    public ResponseEntity<Response> deleteCliente(@PathVariable String dni){
        if(clienteService.findByDnicli(dni).isEmpty()){
            throw new ClienteNotFoundException("No existe un cliente con ese dni");
        }
        if(!reservaService.findByClienteDni(dni).isEmpty()) {
            reservaService.deleteReservasByClienteDni(dni);
        }
        clienteService.deleteCliente(dni);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }*/

    @ExceptionHandler(ClienteNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(ClienteNotFoundException cnfe) {
        Response response = Response.errorResonse(NOT_FOUND, cnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
