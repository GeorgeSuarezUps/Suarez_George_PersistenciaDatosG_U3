package com.ups.persistencia;

import com.ups.persistencia.model.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EcommercePU");
        EntityManager em = emf.createEntityManager();

        // 1. Inserción de datos de prueba
        em.getTransaction().begin();
        Categoria catTech = new Categoria("Tecnología");
        Categoria catHogar = new Categoria("Hogar");
        em.persist(catTech);
        em.persist(catHogar);

        Producto p1 = new Producto("Laptop ASUS VivoBook", 850.00, 12, catTech);
        Producto p2 = new Producto("Teclado Mecánico ENV", 55.00, 30, catTech);
        Producto p3 = new Producto("Mouse Gamer E-YOOSO", 28.50, 45, catTech);
        Producto p4 = new Producto("Lámpara LED Escritorio", 35.00, 20, catHogar);
        em.persist(p1); em.persist(p2); em.persist(p3); em.persist(p4);

        Usuario u1 = new Usuario("George Suarez", "george@ups.edu.ec");
        u1.setPerfil(new PerfilCliente("Sauces 4", "0991234567", "Guayaquil"));
        Usuario u2 = new Usuario("Carlos Mendoza", "carlos@mail.com");
        u2.setPerfil(new PerfilCliente("Av. Amazonas", "0987654321", "Quito"));
        em.persist(u1); em.persist(u2);

        Pedido ped1 = new Pedido(LocalDate.now(), "PAGADO", 905.00, u1);
        ped1.getProductos().add(p1);
        ped1.getProductos().add(p2);

        Pedido ped2 = new Pedido(LocalDate.now().minusDays(2), "PAGADO", 63.50, u2);
        ped2.getProductos().add(p3);
        ped2.getProductos().add(p4);

        Pedido ped3 = new Pedido(LocalDate.now().minusDays(5), "PENDIENTE", 850.00, u1);
        ped3.getProductos().add(p1);

        em.persist(ped1); em.persist(ped2); em.persist(ped3);
        em.getTransaction().commit();

        // CONSULTA JPQL 1: Productos de "Tecnología" con precio > 25, ordenados por precio DESC (Paginación: pág 1, 2 elementos)
        System.out.println("\n--- CONSULTA 1: Productos filtrados, ordenados y paginados ---");
        List<Producto> q1 = em.createQuery(
                "SELECT p FROM Producto p WHERE p.categoria.nombre = :cat AND p.precio > :minPrecio ORDER BY p.precio DESC",
                Producto.class)
                .setParameter("cat", "Tecnología")
                .setParameter("minPrecio", 25.0)
                .setFirstResult(0)
                .setMaxResults(2)
                .getResultList();
        q1.forEach(p -> System.out.println(p.getNombre() + " - $" + p.getPrecio()));

        // CONSULTA JPQL 2: Pedidos en estado "PAGADO" con total >= 50, ordenados por fecha DESC (Paginación)
        System.out.println("\n--- CONSULTA 2: Pedidos pagados ordenados por fecha ---");
        List<Pedido> q2 = em.createQuery(
                "SELECT ped FROM Pedido ped JOIN FETCH ped.usuario u WHERE ped.estado = :est AND ped.total >= :minTotal ORDER BY ped.fecha DESC",
                Pedido.class)
                .setParameter("est", "PAGADO")
                .setParameter("minTotal", 50.0)
                .setFirstResult(0)
                .setMaxResults(5)
                .getResultList();
        q2.forEach(ped -> System.out.println("Pedido #" + ped.getId() + " | Cliente: " + ped.getUsuario().getNombre() + " | Total: $" + ped.getTotal()));

        // CONSULTA JPQL 3: Usuarios de Guayaquil o Quito con pedidos asociados, ordenados por nombre ASC (Paginación)
        System.out.println("\n--- CONSULTA 3: Usuarios por ciudad con pedidos ---");
        List<Usuario> q3 = em.createQuery(
                "SELECT DISTINCT u FROM Usuario u JOIN u.perfil pf JOIN u.pedidos ped WHERE pf.ciudad IN (:c1, :c2) ORDER BY u.nombre ASC",
                Usuario.class)
                .setParameter("c1", "Guayaquil")
                .setParameter("c2", "Quito")
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
        q3.forEach(u -> System.out.println(u.getNombre() + " (" + u.getPerfil().getCiudad() + ")"));

        em.close();
        emf.close();
    }
}