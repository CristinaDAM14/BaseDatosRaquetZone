package com.example.RaquetZone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Empresa")
public class Empresa {

    @Schema(description = "Cif Identificador de la empresa", example = "112345678", required = true)
    @Id
    @Column(name="cif")
    private String cifemp;

    @Schema(description = "Nombre de la empresa", example = "Microsoft", required = true)
    @Column(name = "nombre_social", nullable = false, unique = true)
    private String nomemp;

    @Schema(description = "Url de la web de la empresa", example = "https://www.microsoft.com/es-es", required = true)
    @Column(name = "web", nullable = false)
    private String webemp;

    @Schema(description = "Telefono de la empresa", example = "123456789", required = true)
    @Column(name = "telefono", nullable = false, unique = true)
    private String telemp;

    @Schema(description = "Email de la empresa", example = "microsoft@gmail.com", required = true)
    @Column(name = "email", nullable = false, unique = true)
    private String emailemp;

    @Schema(description = "Direccion de la empresa", example = "Calle colon nº75", required = true)
    @Column(name = "direccion", nullable = false)
    private String direcemp;

    @Schema(description = "Actividad de la empresa", example = "Informatica", required = true)
    @Column(name = "actividad",nullable = false)
    private String activiemp;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Producto> productos = new ArrayList<>();

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Servicio> servicios = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.DETACH)
    @JoinTable(name="empresa_usuario",
            joinColumns = {@JoinColumn(name="cif",referencedColumnName = "cif")},
            inverseJoinColumns = {@JoinColumn(name="dni",referencedColumnName = "dni")})
    @JsonIgnore
    private Set<Usuario> usuario;

    @ManyToMany()
    @JoinTable(name="empresa_cliente",
            joinColumns =  {@JoinColumn(name="cif",referencedColumnName = "cif")},
            inverseJoinColumns = {@JoinColumn(name="dni",referencedColumnName = "dni")})
    @JsonIgnore
    private Set<Cliente> cliente = new HashSet<>();

    public void addCliente(Cliente cliente){
        this.cliente.add(cliente);
        cliente.getEmpresa().add(this);
    }

    public void addUsuario(Usuario usuario){
        this.usuario.add(usuario);
        usuario.getEmpresa().add(this);
    }
}