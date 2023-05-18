package com.example.RaquetZone.controller;

import com.example.RaquetZone.domain.*;
import com.example.RaquetZone.exception.UsuarioNotFoundException;
import com.example.RaquetZone.service.EmpresaService;
import com.example.RaquetZone.service.HorarioService;
import com.example.RaquetZone.service.ReservaService;
import com.example.RaquetZone.service.UsuarioService;
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
import java.sql.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import static com.example.RaquetZone.controller.Response.NOT_FOUND;

@RestController
@Tag(name = "Usuario", description = "Lista de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ReservaService reservaService;
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private HorarioService horarioService;

    @Operation(summary = "Obtiene el listado de usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de usuarios",
                    content = @Content(array = @ArraySchema(schema =@Schema(implementation = Usuario.class))))
    })
    @GetMapping(value = "/usuarios", produces = "application/json")
    public ResponseEntity<Set<Usuario>> getUsuarios(){
        Set<Usuario> usuarios = null;
        usuarios = usuarioService.findAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un usuario determinado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el usuario",
                    content = @Content(schema = @Schema(implementation =
                    Usuario.class))),
    @ApiResponse(responseCode = "404", description = "El usuario no existe",
            content = @Content(schema = @Schema(implementation =
            Response.class)))
})
    @GetMapping(value = "/usuario/{dni}", produces = "application/json")
    public ResponseEntity<Usuario> getUsuario(@PathVariable String dni){
        Usuario usuario = usuarioService.findByDniusr(dni).orElseThrow(() -> new UsuarioNotFoundException(dni));
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un usuario determinado por su nombre y password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el usuario",
                    content = @Content(schema = @Schema(implementation =
                            Usuario.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/usuario/login",produces = "application/json")
    public ResponseEntity<Set<Usuario>> getUsuariosByNombreusrAndPasswordusr(@RequestParam(value = "dni", defaultValue = "") String dniusr,
                                                                      @RequestParam(value = "password", defaultValue = "") String passwordusr){

        String sha256hex = Hashing.sha256()
                .hashString(passwordusr, StandardCharsets.UTF_8)
                .toString();
        Set<Usuario> usuario = usuarioService.findByDniusrAndPasswordusr(dniusr,sha256hex);

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un usuario determinado por su nombre, email o rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el usuario",
                    content = @Content(schema = @Schema(implementation =
                            Usuario.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/usuario/NomEmailRol",produces = "application/json")
    public ResponseEntity<Set<Usuario>> getUsuariosByNombreEmailRol(@RequestParam Map<String,String> allParams){
        Set<Usuario> usuarios = null;
        if(allParams.containsKey("nombre")){
            String nombreUsuario = allParams.get("nombre");
            if(nombreUsuario.equals(""))
                usuarios = usuarioService.findAll();
            else
                usuarios = usuarioService.findByNombreusr(nombreUsuario);
        }else if(allParams.containsKey("email")){
            String emailUsuario = allParams.get("email");
            if(emailUsuario.equals(""))
                usuarios = usuarioService.findAll();
            else
                usuarios = usuarioService.findByEmailusr(emailUsuario);
        }else if(allParams.containsKey("rol")){
            int rolUsuario = Integer.parseInt(allParams.get("rol"));
            usuarios = usuarioService.findByRolusr(rolUsuario);
        }else{
            usuarios = usuarioService.findAll();
        }
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @Operation(summary = "Recupera la contraseña de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseña recuperada",
                    content = @Content(schema = @Schema(implementation =
                            Usuario.class))),
            @ApiResponse(responseCode = "404", description = "ERROR",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/usuario/recuperarpassword",produces = "application/json")
    public ResponseEntity<Set<Usuario>> getPassword(@RequestParam(value = "email", defaultValue = "") String emailusr){

        Set<Usuario> usuarios = usuarioService.recuperarPassword(emailusr);

        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene los horarios de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el usuario",
                    content = @Content(schema = @Schema(implementation =
                            Usuario.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/usuario/{dni}/horarios", produces = "application/json")
    public ResponseEntity<Set<Horario>> getUsuarioHorarios(@PathVariable String dni){
        if (usuarioService.findByDniusr(dni).isEmpty()){
            throw new UsuarioNotFoundException("El usuario no existe");
        }
        Set<Horario> horarios = horarioService.findByUsuarioDniusr(dni);
        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene los empleados que no tienen vacaciones en una fecha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el usuario",
                    content = @Content(schema = @Schema(implementation =
                            Usuario.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/usuarios/{cif}/horarios/{fecha}", produces = "application/json")
    public ResponseEntity<Set<Usuario>> getEmpleadosSinVacaciones(@PathVariable String cif,@PathVariable Date fecha){
        Set<Usuario> usuarios = usuarioService.findUsuariosByEmpresaCifempAndRolusr(cif,1);

        usuarios.removeIf(usuario -> !horarioService.findByUsuarioDniusrAndFechahor(usuario.getDniusr(), fecha).isEmpty());


        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene los reservas de una fecha de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el usuario",
                    content = @Content(schema = @Schema(implementation =
                            Usuario.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe",
                    content = @Content(schema = @Schema(implementation =
                            Response.class)))
    })
    @GetMapping(value = "/usuario/{dni}/reservas/{fecha}", produces = "application/json")
    public ResponseEntity<Set<Reserva>> getUsuarioReservasByFecha(@PathVariable String dni, @PathVariable Date fecha){
        if (usuarioService.findByDniusr(dni).isEmpty()){
            throw new UsuarioNotFoundException("El usuario no existe");
        }
        Set<Reserva> reservas = reservaService.findByUsuarioDniusrAndFechaRes(dni,fecha);
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el usuario", content = @Content
                    (schema = @Schema(implementation = Usuario.class)))
    })
    @PostMapping(value = "/empresa/{cif}/usuario", produces = "application/json", consumes = "application/json")
        public ResponseEntity<Usuario> addUsuario(@PathVariable(value = "cif") String cifemp, @RequestBody Usuario addUsuario){
        if(addUsuario.getRolusr() < 1 || addUsuario.getRolusr() > 3){
            throw new UsuarioNotFoundException("El rol de usuario tiene que estar entre 1 y 3");
        }
        Usuario usuario = empresaService.findByCifemp(cifemp).map(empresa -> {
            String sha256hex = Hashing.sha256()
                    .hashString(addUsuario.getPasswordusr(), StandardCharsets.UTF_8)
                    .toString();
            addUsuario.setPasswordusr(sha256hex);
            empresa.addUsuario(addUsuario);
            return usuarioService.addUsuario(addUsuario);
        }).orElseThrow(() -> new UsuarioNotFoundException("Usuario mal insertado"));

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @Operation(summary = "Modifica un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica el usuario", content = @Content
                    (schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe", content = @Content
                    (schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/usuario/modify/{dni}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Usuario> modifyUsuario(@PathVariable String dni, @RequestBody Usuario newUsuario){
        String sha2456hex = Hashing.sha256()
                .hashString(newUsuario.getPasswordusr(),StandardCharsets.UTF_8)
                .toString();
        newUsuario.setPasswordusr(sha2456hex);
        Usuario usuario = usuarioService.modifyUsuario(dni, newUsuario);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    /*@Operation(summary = "Elimina un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el usuario", content = @Content
                    (schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe", content = @Content
                    (schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/usuario/delete/{dni}", produces = "application/json")
    public ResponseEntity<Response> deleteUsuario(@PathVariable String dni){
        int rolusr = usuarioService.findByDniusr(dni).get().getRolusr();
        if(rolusr != 3) {
            usuarioService.deleteUsuario(dni);
        }else{
            System.out.println("Nos han intentado borrar un superAdmin");
        }
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }*/

    @ExceptionHandler(UsuarioNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(UsuarioNotFoundException pnfe) {
        Response response = Response.errorResonse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
