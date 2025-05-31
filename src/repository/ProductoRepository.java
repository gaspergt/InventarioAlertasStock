package repository;

import model.Producto;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ProductoRepository {

    public Map<Integer, Producto> obtenerTodosLosProductos() {
        Map<Integer, Producto> productos = new HashMap<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM productos")) {

            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("stock"),
                        rs.getInt("stock_minimo"),
                        rs.getDouble("precio")
                );
                productos.put(producto.getId(), producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    public Producto obtenerProductoPorId(int idProducto) {
        Producto producto = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM productos WHERE id = ?")) {
            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                producto = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("stock"),
                        rs.getInt("stock_minimo"),
                        rs.getDouble("precio")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

    public void actualizarStockProducto(int idProducto, int nuevoStock) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE productos SET stock = ? WHERE id = ?")) {
            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregarNuevoProducto(String nombre, int stock, int stockMinimo, double precio) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO productos (nombre, stock, stock_minimo, precio) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, nombre);
            stmt.setInt(2, stock);
            stmt.setInt(3, stockMinimo);
            stmt.setDouble(4, precio);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void abastecerProducto(int idProducto, int cantidadAgregar) {
        Producto producto = obtenerProductoPorId(idProducto);
        if (producto != null) {
            int nuevoStock = producto.getStock() + cantidadAgregar;
            actualizarStockProducto(idProducto, nuevoStock);
        }
    }
}
