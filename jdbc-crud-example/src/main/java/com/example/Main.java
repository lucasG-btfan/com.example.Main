package com.example;

import com.example.dao.ProductoDAO;
import com.example.dao.ProductoDAOImpl;
import com.example.model.Producto;
import com.example.util.DatabaseUtil;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Inicializar la base de datos
        DatabaseUtil.initDatabase();

        // Crear instancia del DAO

        ProductoDAO productoDAO = new ProductoDAOImpl();
        System.out.println("=== DEMOSTRACIÓN DE OPERACIONES CRUD CON JDBC ===");

        // CREATE - Crear nuevos productos
        System.out.println("\n=== CREATE ===");
        Producto nuevoProducto = new Producto("Laptop HP",
                "Laptop HP Core i5 8GB RAM", 899.99, 10);

        int idGenerado = productoDAO.crear(nuevoProducto);
        System.out.println("Producto creado con ID: " + idGenerado);

        // Crear otro producto
        Producto otroProducto = new Producto("Monitor Dell",
                "Monitor Dell 27 pulgadas 4K", 349.99, 15);
        int idOtroProducto = productoDAO.crear(otroProducto);

        System.out.println("Producto creado con ID: " + idOtroProducto);
        // READ - Leer productos
        System.out.println("\n=== READ ===");

        // Buscar por ID

        Producto productoEncontrado = productoDAO.buscarPorId(idGenerado);
        System.out.println("Producto encontrado por ID: " + productoEncontrado);

        // Listar todos

        System.out.println("\nLista de todos los productos:");
        List<Producto> todosLosProductos = productoDAO.listarTodos();

        for (Producto p : todosLosProductos) {
            System.out.println(p);
        }

        // UPDATE - Actualizar producto

        System.out.println("\n=== UPDATE ===");
        productoEncontrado.setPrecio(799.99);
        productoEncontrado.setStock(20);

        boolean actualizado = productoDAO.actualizar(productoEncontrado);

        System.out.println("Producto actualizado: " + actualizado);

        // Verificar la actualización

        Producto productoActualizado = productoDAO.buscarPorId(idGenerado);
        System.out.println("Producto después de actualizar: " + productoActualizado);

        // DELETE - Eliminar producto

        System.out.println("\n=== DELETE ===");
        boolean eliminado = productoDAO.eliminar(idOtroProducto);
        System.out.println("Producto eliminado: " + eliminado);

        // Verificar la eliminación

        System.out.println("\nLista de productos después de eliminar:");
        todosLosProductos = productoDAO.listarTodos();

        for (Producto p : todosLosProductos) {
            System.out.println(p);
        }
    }
}