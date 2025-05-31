package service;

import model.Producto;
import repository.ProductoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventarioService {
    private ProductoRepository productoRepository;

    public InventarioService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> verificarStockBajo() {
        List<Producto> productosBajoStock = new ArrayList<>();
        for (Producto producto : productoRepository.obtenerTodosLosProductos().values()) {
            if (producto.getStock() <= producto.getStockMinimo()) {
                productosBajoStock.add(producto);
            }
        }
        return productosBajoStock;
    }
}
