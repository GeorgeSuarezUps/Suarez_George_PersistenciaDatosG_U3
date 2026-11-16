# Diseño y Consulta de un Modelo ORM con JPA/Hibernate

Proyecto académico correspondiente a la **Unidad 3: Lenguajes de Consultas de Datos** de la asignatura **Persistencia de Datos** (Carrera de Ingeniería en Software — Universidad Politécnica Salesiana).

## Descripción del Proyecto
Implementación de un modelo de mapeo objeto-relacional (ORM) basado en un caso de estudio de comercio electrónico (*E-Commerce*) utilizando **Jakarta Persistence (JPA)**, **Hibernate ORM 6** y base de datos **H2**.

## Arquitectura del Modelo (Entidades y Relaciones)
El dominio está compuesto por cinco entidades persistentes ubicadas en el paquete `com.ups.persistencia.model`:
* **Usuario — PerfilCliente (`1:1`):** Relación bidireccional mediante `@OneToOne` y clave foránea única.
* **Usuario — Pedido (`1:N`):** Un cliente puede registrar múltiples órdenes de compra (`@OneToMany` / `@ManyToOne`).
* **Categoria — Producto (`1:N`):** Clasificación jerárquica del catálogo de productos.
* **Pedido — Producto (`N:M`):** Asociación mediante tabla intermedia `pedido_producto` (`@ManyToMany` y `@JoinTable`).

## Consultas JPQL Implementadas
En `com.ups.persistencia.Main` se ejecutan tres consultas tipadas (`TypedQuery`) con parámetros nombrados, ordenamiento (`ORDER BY`) y paginación (`setFirstResult` / `setMaxResults`):
1. **Catálogo filtrado:** Productos de una categoría específica superiores a un precio mínimo, ordenados descendentemente por precio.
2. **Auditoría de pedidos:** Pedidos pagados con monto mínimo, empleando `JOIN FETCH` para evitar consultas $N+1$, ordenados por fecha descendente.
3. **Segmentación geográfica:** Usuarios únicos con pedidos registrados en ciudades seleccionadas, ordenados alfabéticamente.

## Tecnologías Utilizadas
* Java 21 / Maven
* Hibernate ORM 6.6.1.Final (Jakarta Persistence 3.1)
* H2 Database Engine 2.3.232
