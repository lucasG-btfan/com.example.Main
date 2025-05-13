package com.example.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {

    private static final String DB_URL = "jdbc:h2:./data/productosdb";

    private static final String DB_USER = "sa";

    private static final String DB_PASSWORD = "";

    // Método para obtener una conexión

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Método para cerrar recursos

    public static void closeResources(Connection conn, Statement stmt) {
        try {

            if (stmt != null) stmt.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        try {

            if (conn != null) conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para inicializar la base de datos

    public static void initDatabase() {
        Connection conn = null;

        Statement stmt = null;

        try {

            conn = getConnection();

            stmt = conn.createStatement();

            // Crear tabla productos si no existe

            String sql = "CREATE TABLE IF NOT EXISTS productos (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nombre VARCHAR(100) NOT NULL, " +
                    "descripcion VARCHAR(255), " +
                    "precio DECIMAL(10,2) NOT NULL, " +
                    "stock INT DEFAULT 0)";

            stmt.execute(sql);

            System.out.println("Base de datos inicializada correctamente");

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {
            closeResources(conn, stmt);
        }
    }
}