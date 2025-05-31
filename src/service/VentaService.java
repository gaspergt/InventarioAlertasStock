package service;

import model.Producto;
import model.Usuario;
import model.Venta;
import repository.ProductoRepository;
import repository.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VentaService {
    private Map<Integer, Venta> ventas;
    private ProductoRepository productoRepository;

    public VentaService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
        ventas = new ConcurrentHashMap<>();
    }

    public boolean realizarVenta(int idProducto, int cantidad, Usuario usuario) {
        Producto producto = productoRepository.obtenerProductoPorId(idProducto);

        if (producto != null && producto.getStock() >= cantidad) {
            int nuevoStock = producto.getStock() - cantidad;
            productoRepository.actualizarStockProducto(idProducto, nuevoStock);

            guardarVentaEnBaseDeDatos(idProducto, cantidad, usuario);

            Venta venta = new Venta(idProducto, cantidad, new Date(), usuario);
            ventas.put(ventas.size() + 1, venta);

            return true;
        } else {
            return false;
        }
    }

    private void guardarVentaEnBaseDeDatos(int idProducto, int cantidad, Usuario usuario) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO ventas (id_producto, cantidad, fecha, id_usuario) VALUES (?, ?, CURDATE(), ?)")) {
            stmt.setInt(1, idProducto);
            stmt.setInt(2, cantidad);
            stmt.setInt(3, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Venta> obtenerVentas() {
        return ventas;
    }
}
