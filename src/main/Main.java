package main;

import model.Producto;
import model.Usuario;
import model.Venta;
import repository.ProductoRepository;
import repository.UsuarioRepository;
import service.InventarioService;
import service.ReporteService;
import service.VentaService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductoRepository productoRepository = new ProductoRepository();
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        InventarioService inventarioService = new InventarioService(productoRepository);
        VentaService ventaService = new VentaService(productoRepository);
        ReporteService reporteService = new ReporteService();

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Sistema de Gestión de Inventario ---");
            System.out.println("1. Mostrar productos");
            System.out.println("2. Realizar venta");
            System.out.println("3. Verificar stock bajo");
            System.out.println("4. Abastecer producto existente");
            System.out.println("5. Agregar nuevo producto");
            System.out.println("6. Reporte de ventas");
            System.out.println("7. Exportar reporte de ventas a CSV");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("\nLista de Productos:");
                    for (Producto p : productoRepository.obtenerTodosLosProductos().values()) {
                        System.out.println(p);
                    }
                    break;
                case 2:
                    System.out.print("\nIngrese ID del producto: ");
                    int idProducto = scanner.nextInt();

                    Producto producto = productoRepository.obtenerProductoPorId(idProducto);

                    if (producto == null) {
                        System.out.println("Error: Producto no encontrado.");
                        break;
                    }

                    System.out.print("Ingrese cantidad a vender: ");
                    int cantidad = scanner.nextInt();

                    System.out.println("\nLista de Usuarios:");
                    List<Usuario> usuarios = usuarioRepository.obtenerTodosLosUsuarios();
                    for (Usuario u : usuarios) {
                        System.out.println(u);
                    }

                    System.out.print("Ingrese ID del usuario que realiza la venta: ");
                    int idUsuario = scanner.nextInt();
                    Usuario usuario = usuarioRepository.obtenerUsuarioPorId(idUsuario);

                    if (usuario == null) {
                        System.out.println("Error: Usuario no encontrado.");
                        break;
                    }

                    boolean exito = ventaService.realizarVenta(idProducto, cantidad, usuario);
                    if (exito) {
                        producto = productoRepository.obtenerProductoPorId(idProducto); 
                        System.out.println("Venta realizada con éxito. Stock actualizado:");
                        System.out.println(producto);
                    } else {
                        System.out.println("Error: Stock insuficiente.");
                    }
                    break;
                case 3:
                    System.out.println("\nProductos con stock bajo:");
                    List<Producto> productosBajoStock = inventarioService.verificarStockBajo();
                    if (productosBajoStock.isEmpty()) {
                        System.out.println("Todos los productos están con stock suficiente.");
                    } else {
                        for (Producto p : productosBajoStock) {
                            System.out.println(p);
                        }
                    }
                    break;
                case 4:
                    System.out.print("\nIngrese ID del producto a abastecer: ");
                    int idAbastecer = scanner.nextInt();
                    System.out.print("Ingrese cantidad a agregar: ");
                    int cantidadAgregar = scanner.nextInt();
                    productoRepository.abastecerProducto(idAbastecer, cantidadAgregar);
                    System.out.println("Producto abastecido correctamente.");
                    break;
                case 5:
                    scanner.nextLine(); 
                    System.out.print("\nIngrese nombre del nuevo producto: ");
                    String nombreNuevo = scanner.nextLine();
                    System.out.print("Ingrese stock inicial: ");
                    int stockNuevo = scanner.nextInt();
                    System.out.print("Ingrese stock mínimo: ");
                    int stockMinimoNuevo = scanner.nextInt();
                    System.out.print("Ingrese precio: ");
                    double precioNuevo = scanner.nextDouble();
                    productoRepository.agregarNuevoProducto(nombreNuevo, stockNuevo, stockMinimoNuevo, precioNuevo);
                    System.out.println("Producto agregado correctamente.");
                    break;
                case 6:
                    System.out.println("\nReporte de Ventas:");
                    Map<Integer, Venta> ventas = ventaService.obtenerVentas();
                    if (ventas.isEmpty()) {
                        System.out.println("No hay ventas registradas.");
                    } else {
                        for (Venta v : ventas.values()) {
                            System.out.println(v);
                        }
                    }
                    break;
                case 7:
                    System.out.print("\nIngrese nombre del archivo CSV a exportar (ej: reporte.csv): ");
                    scanner.nextLine(); 
                    String nombreArchivo = scanner.nextLine();
                    reporteService.exportarVentasACSV(ventaService.obtenerVentas(), nombreArchivo);
                    break;
                case 8:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 8);

        scanner.close();
    }
}
