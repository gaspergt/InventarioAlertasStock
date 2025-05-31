package model;

public class Producto {
    private int id;
    private String nombre;
    private int stock;
    private int stockMinimo;
    private double precio;

    public Producto(int id, String nombre, int stock, int stockMinimo, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.precio = precio;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getStock() { return stock; }
    public int getStockMinimo() { return stockMinimo; }
    public double getPrecio() { return precio; }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %-3d | Nombre: %-15s | Stock: %-5d | Stock Mínimo: %-5d | Precio: Q%-8.2f",
                id, nombre, stock, stockMinimo, precio
        );
    }
}
