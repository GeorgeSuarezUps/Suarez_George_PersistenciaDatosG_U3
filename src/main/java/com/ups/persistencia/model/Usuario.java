package com.ups.persistencia.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nombre;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PerfilCliente perfil;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();

    public Usuario() {}
    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public void setPerfil(PerfilCliente perfil) {
        this.perfil = perfil;
        perfil.setUsuario(this);
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getNombre() { return nombre; }
    public PerfilCliente getPerfil() { return perfil; }
    public List<Pedido> getPedidos() { return pedidos; }
}