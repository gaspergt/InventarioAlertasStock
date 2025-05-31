package model;

import java.util.Date;

public class Venta {
    private int idProducto;
    private int cantidad;
    private Date fecha;
    private Usuario usuario;

    public Venta(int idProducto, int cantidad, Date fecha, Usuario usuario) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public int getIdProducto() { return idProducto; }
    public int getCantidad() { return cantidad; }
    public Date getFecha() { return fecha; }
    public Usuario getUsuario() { return usuario; }

    @Override
    public String toString() {
        return String.format("Producto ID: %-3d | Cantidad: %-3d | Fecha: %tF | Vendido por: %s",
                idProducto, cantidad, fecha, usuario.getNombre());
    }
}
