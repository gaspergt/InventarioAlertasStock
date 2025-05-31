package service;

import model.Venta;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ReporteService {

    public void exportarVentasACSV(Map<Integer, Venta> ventas, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.append("ID_Venta,ProductoID,Cantidad,Fecha,Usuario\n");
            int idVenta = 1;
            for (Venta venta : ventas.values()) {
                writer.append(String.format("%d,%d,%d,%tF,%s\n",
                        idVenta++,
                        venta.getIdProducto(),
                        venta.getCantidad(),
                        venta.getFecha(),
                        venta.getUsuario().getNombre()));
            }
            System.out.println("Reporte exportado exitosamente a: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error exportando el archivo: " + e.getMessage());
        }
    }
}
