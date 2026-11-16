package com.ups.persistencia.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private String estado;
    private Double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
        name = "pedido_producto",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos = new ArrayList<>();

    public Pedido() {}
    public Pedido(LocalDate fecha, String estado, Double total, Usuario usuario) {
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.usuario = usuario;
    }

    public Long getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public Double getTotal() { return total; }
    public Usuario getUsuario() { return usuario; }
    public List<Producto> getProductos() { return productos; }
}