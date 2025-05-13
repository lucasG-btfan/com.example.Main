package com.example.dao;

import com.example.model.Producto;
import com.example.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    @Override

    public int crear(Producto producto) {
        Connection conn = null;

        PreparedStatement stmt = null;

        ResultSet rs = null;

        int generatedId = -1;

        try {
            conn = DatabaseUtil.getConnection();

            String sql = "INSERT INTO productos (nombre, descripcion, precio, stock) VALUES (?, ?, ?, ?)";

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, producto.getNombre());

            stmt.setString(2, producto.getDescripcion());

            stmt.setDouble(3, producto.getPrecio());

            stmt.setInt(4, producto.getStock());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {

                rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }

            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            // Cerrar ResultSet

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DatabaseUtil.closeResources(conn, stmt);
        }
        return generatedId;
    }

    @Override

    public Producto buscarPorId(int id) {

        Connection conn = null;

        PreparedStatement stmt = null;

        ResultSet rs = null;

        Producto producto = null;

        try {
            conn = DatabaseUtil.getConnection();

            String sql = "SELECT * FROM productos WHERE id = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                producto = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar ResultSet

            if (rs != null) {

                try {

                    rs.close();

                } catch (SQLException e) {

                    e.printStackTrace();

                }

            }

            DatabaseUtil.closeResources(conn, stmt);

        }
        return producto;
    }

    @Override

    public List<Producto> listarTodos() {

        Connection conn = null;

        Statement stmt = null;

        ResultSet rs = null;

        List<Producto> productos = new ArrayList<>();

        try {

            conn = DatabaseUtil.getConnection();

            stmt = conn.createStatement();

            String sql = "SELECT * FROM productos";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {

                Producto producto = new Producto(

                        rs.getInt("id"),

                        rs.getString("nombre"),

                        rs.getString("descripcion"),

                        rs.getDouble("precio"),

                        rs.getInt("stock")

                );

                productos.add(producto);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            // Cerrar ResultSet

            if (rs != null) {

                try {

                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            DatabaseUtil.closeResources(conn, stmt);

        }

        return productos;

    }

    @Override

    public boolean actualizar(Producto producto) {
        Connection conn = null;

        PreparedStatement stmt = null;

        boolean actualizado = false;

        try {

            conn = DatabaseUtil.getConnection();

            String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, stock = ? WHERE id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getId());


            int affectedRows = stmt.executeUpdate();

            actualizado = (affectedRows > 0);

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            DatabaseUtil.closeResources(conn, stmt);

        }

        return actualizado;

    }

    @Override

    public boolean eliminar(int id) {

        Connection conn = null;

        PreparedStatement stmt = null;

        boolean eliminado = false;

        try {

            conn = DatabaseUtil.getConnection();

            String sql = "DELETE FROM productos WHERE id = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();

            eliminado = (affectedRows > 0);

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {
            DatabaseUtil.closeResources(conn, stmt);
        }
        return eliminado;
    }
}