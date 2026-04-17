package co.edu.udistrital.model.repository;

import co.edu.udistrital.model.entities.*;
import java.util.List;
import java.sql.Connection; // IMPORTANTE: Usa java.sql, no org.mariadb
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

/**
 *
 * @author Acer-Pc
 */
public class Migrator_unused {

    private ClienteRepository clienteRepo = new ClienteRepository();
    private JuegoRepository juegoRepo = new JuegoRepository();
    private PeliculaRepository peliculaRepo = new PeliculaRepository();
    private AlquilerRepository alquilerRepo = new AlquilerRepository();
    private EmpleadoRepository empleadoRepo = new EmpleadoRepository();

    public void run() {
        if (migrarJuegos()) {
            System.out.print("Juegos migrados correctamente");
        }
        if (migrarPeliculas()) {
            System.out.print("Peliculas migrados correctamente");
        }
        if (migrarClientes()) {
            System.out.print("Clientes migrados correctamente");
        }
        if (migrarEmpleados()) {
            System.out.print("Empleados migrados correctamente");
        }

    }

    private boolean migrarJuegos() {
        // 1. Obtenemos los datos que ya tienes en memoria/JSON
        List<Juego> listaActual = juegoRepo.obtenerTodos();

        String sqlProducto = "INSERT INTO productos (nombre, costo, stock, alquilados, tipo_producto) VALUES (?, ?, ?, ?, 'JUEGO')";
        String sqlJuego = "INSERT INTO juegos (id_producto, plataforma, genero) VALUES (?, ?, ?)";

        // 2. Abrimos la conexión (Try-with-resources para cerrar todo al final)
        try {
            Class.forName(Config.DRIVER);
            try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS)) {

                for (Juego j : listaActual) {
                    // INSERT EN TABLA PADRE
                    try (PreparedStatement psP = con.prepareStatement(sqlProducto, Statement.RETURN_GENERATED_KEYS)) {
                        psP.setString(1, j.getNombreProducto());
                        psP.setDouble(2, j.getCostoBase());
                        psP.setInt(3, j.getStock());
                        psP.setInt(4, j.getAlquilados());
                        psP.executeUpdate();

                        // Recuperamos el ID autoincremental que asignó la DB
                        ResultSet rs = psP.getGeneratedKeys();
                        if (rs.next()) {
                            int idGenerado = rs.getInt(1);

                            // INSERT EN TABLA HIJA
                            try (PreparedStatement psJ = con.prepareStatement(sqlJuego)) {
                                psJ.setInt(1, idGenerado);
                                psJ.setString(2, j.getPlataforma());
                                psJ.setString(3, j.getGenero());
                                psJ.executeUpdate();
                            }
                        }
                    }
                }
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error en migración: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private boolean migrarPeliculas() {
        // 1. Obtenemos los datos que ya tienes en memoria/JSON
        List<Pelicula> listaActual = peliculaRepo.obtenerTodos();

        String sqlProducto = "INSERT INTO productos (nombre, costo, stock, alquilados, tipo_producto) VALUES (?, ?, ?, ?, 'JUEGO')";
        String sqlPelicula = "INSERT INTO peliculas (id_producto, formato, duracion) VALUES (?, ?, ?)";

        // 2. Abrimos la conexión (Try-with-resources para cerrar todo al final)
        try {
            Class.forName(Config.DRIVER);
            try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS)) {

                for (Pelicula p : listaActual) {
                    // INSERT EN TABLA PADRE
                    try (PreparedStatement psP = con.prepareStatement(sqlProducto, Statement.RETURN_GENERATED_KEYS)) {
                        psP.setString(1, p.getNombreProducto());
                        psP.setDouble(2, p.getCostoBase());
                        psP.setInt(3, p.getStock());
                        psP.setInt(4, p.getAlquilados());
                        psP.executeUpdate();

                        // Recuperamos el ID autoincremental que asignó la DB
                        ResultSet rs = psP.getGeneratedKeys();
                        if (rs.next()) {
                            int idGenerado = rs.getInt(1);

                            // INSERT EN TABLA HIJA
                            try (PreparedStatement psJ = con.prepareStatement(sqlPelicula)) {
                                psJ.setInt(1, idGenerado);
                                psJ.setString(2, p.getFormato());
                                psJ.setString(3, p.getDuracion());
                                psJ.executeUpdate();
                            }
                        }
                    }
                }
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error en migración: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private boolean migrarClientes() {
        // 1. Obtenemos los datos que ya tienes en memoria/JSON
        List<Cliente> listaActual = clienteRepo.obtenerTodos();

        String sqlCuenta = "INSERT INTO cuentas (username, nombre, contrasenia, correo, telefono) VALUES (?, ?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO clientes (id_cuenta, saldo, membresia, fecha_membresia) VALUES (?, ?, ?, ?)";

        // 2. Abrimos la conexión (Try-with-resources para cerrar todo al final)
        try {
            Class.forName(Config.DRIVER);
            try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS)) {

                for (Cliente c : listaActual) {
                    // INSERT EN TABLA PADRE
                    try (PreparedStatement psC = con.prepareStatement(sqlCuenta, Statement.RETURN_GENERATED_KEYS)) {
                        psC.setString(1, c.getNombreUsuario());
                        psC.setString(2, c.getNombreCompleto());
                        psC.setString(3, c.getContrasenia());
                        psC.setString(4, c.getCorreo());
                        psC.setString(5, c.getTelefono());
                        psC.executeUpdate();

                        // Recuperamos el ID autoincremental que asignó la DB
                        ResultSet rs = psC.getGeneratedKeys();
                        if (rs.next()) {
                            int idGenerado = rs.getInt(1);

                            // INSERT EN TABLA HIJA
                            try (PreparedStatement psCL = con.prepareStatement(sqlCliente)) {
                                psCL.setInt(1, idGenerado);
                                psCL.setDouble(2, c.getSaldo());
                                psCL.setString(3, c.getMembresia().toString());
                                psCL.setDate(4, Date.valueOf(c.getFechaPagoMembresia()));
                                psCL.executeUpdate();
                            }
                        }
                    }
                }
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error en migración: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private boolean migrarEmpleados() {
        // 1. Obtenemos los datos que ya tienes en memoria/JSON
        List<Empleado> listaActual = empleadoRepo.obtenerTodos();

        String sqlCuenta = "INSERT INTO cuentas (username, nombre, contrasenia, correo, telefono) VALUES (?, ?, ?, ?, ?)";
        String sqlEmpleado = "INSERT INTO empleados (id_cuenta, dni, direccion, cargo, rol) VALUES (?, ?, ?, ?, 'EMPLEADO')";

        // 2. Abrimos la conexión (Try-with-resources para cerrar todo al final)
        try {
            Class.forName(Config.DRIVER);
            try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS)) {

                for (Empleado e : listaActual) {
                    // INSERT EN TABLA PADRE
                    try (PreparedStatement psC = con.prepareStatement(sqlCuenta, Statement.RETURN_GENERATED_KEYS)) {
                        psC.setString(1, e.getNombreUsuario());
                        psC.setString(2, e.getNombreCompleto());
                        psC.setString(3, e.getContrasenia());
                        psC.setString(4, e.getCorreo());
                        psC.setString(5, e.getTelefono());
                        psC.executeUpdate();

                        // Recuperamos el ID autoincremental que asignó la DB
                        ResultSet rs = psC.getGeneratedKeys();
                        if (rs.next()) {
                            int idGenerado = rs.getInt(1);

                            // INSERT EN TABLA HIJA
                            try (PreparedStatement psE = con.prepareStatement(sqlEmpleado)) {
                                psE.setInt(1, idGenerado);
                                psE.setString(2, e.getDni());
                                psE.setString(3, e.getDireccion());
                                psE.setString(4, e.getCargo());
                                psE.executeUpdate();
                            }
                        }
                    }
                }
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error en migración: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
