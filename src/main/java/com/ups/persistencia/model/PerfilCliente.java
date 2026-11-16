package com.ups.persistencia.model;

import jakarta.persistence.*;

@Entity
@Table(name = "perfiles_clientes")
public class PerfilCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccion;
    private String telefono;
    private String ciudad;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", unique = true)
    private Usuario usuario;

    public PerfilCliente() {}
    public PerfilCliente(String direccion, String telefono, String ciudad) {
        this.direccion = direccion;
        this.telefono = telefono;
        this.ciudad = ciudad;
    }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getCiudad() { return ciudad; }
    public String getDireccion() { return direccion; }
}