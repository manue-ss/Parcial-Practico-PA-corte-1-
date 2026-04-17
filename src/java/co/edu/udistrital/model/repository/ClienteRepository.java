package co.edu.udistrital.model.repository;

import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.enums.TipoMembresia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para gestionar clientes usando MariaDB.
 *
 * @author Manuel Salazar
 */
public class ClienteRepository {

    public ClienteRepository() {
    }

    /*------------------->>C-Create<<-------------------*/
    /**
     * Guarda un nuevo cliente. Si ya existe, devuelve error de consola.
     *
     * @param cliente El cliente a guardar.
     * @return verdadero si se añadió satisfactoriamente, falso en caso
     * contrario.
     */
    public boolean addCliente(Cliente cliente) {
        String sqlCuenta = "INSERT INTO cuentas (username, nombre, contrasenia, correo, telefono) VALUES (?, ?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO clientes (id_cuenta, saldo, membresia, fecha_membresia) VALUES (?, ?, ?, ?)";

        try {
            Class.forName(Config.DRIVER);

            try (Connection con = DriverManager.getConnection(Config.URL,
                    Config.USER, Config.PASS)) {
                try (PreparedStatement psC = con.prepareStatement(sqlCuenta,
                        Statement.RETURN_GENERATED_KEYS)) {
                    psC.setString(1, cliente.getNombreUsuario());
                    psC.setString(2, cliente.getNombreCompleto());
                    psC.setString(3, cliente.getContrasenia());
                    psC.setString(4, cliente.getCorreo());
                    psC.setString(5, cliente.getTelefono());
                    psC.executeUpdate();

                    ResultSet rs = psC.getGeneratedKeys();
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);

                        try (PreparedStatement psCL = con.prepareStatement(
                                sqlCliente)) {
                            psCL.setInt(1, idGenerado);
                            psCL.setDouble(2, cliente.getSaldo());
                            psCL.setString(3, cliente.getMembresia().toString());
                            psCL.setDate(4, Date.valueOf(
                                    cliente.getFechaPagoMembresia()));
                            psCL.executeUpdate();
                        }
                    }
                }

                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error en guardado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /*------------------->>R-Read<<-------------------*/
    /**
     * Generaliza el proceso de llamado de un cliente segun diferentes
     * parametros.
     *
     * @param sql Sentencia sql para el llamado de los datos.
     * @param param Parametro asociado a la sentecia.
     * @return un obejto cliente segun el parametroo indicado.
     */
    private Cliente getCliente(String sql, Object param) {
        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, param);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    // Datos de la cuenta (Padre)
                    String username = rs.getString("username");
                    String nombre = rs.getString("nombre");
                    String contrasenia = rs.getString("contrasenia");
                    String correo = rs.getString("correo");
                    String telefono = rs.getString("telefono");

                    // Datos del cliente (Hijo)
                    int idInt = rs.getInt("id_cuenta");
                    String idStr = "Cl" + String.format("%05d", idInt);
                    double saldo = rs.getDouble("saldo");
                    String membresiaStr = rs.getString("membresia");
                    Date fechaSql = rs.getDate("fecha_membresia");

                    System.out.println("Cliente encontrado: " + nombre);

                    TipoMembresia membresia = TipoMembresia.valueOf(membresiaStr);
                    LocalDate fechaMembresia = fechaSql.toLocalDate();
                    return new Cliente(saldo, fechaMembresia,
                            membresia, idStr, username,
                            contrasenia, nombre, correo, telefono);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en getById: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Obtiene un cliente por id de usuario.
     *
     * @param idBuscado id del cliente
     * @return El cliente o null si no existe.
     */
    public Cliente getById(String idBuscado) {
        int idNumerico = Integer.parseInt(idBuscado.replaceAll("[^0-9]", ""));

        String sql = "SELECT cl.*, cu.username, cu.nombre, cu.contrasenia, cu.correo, cu.telefono "
                + "FROM clientes cl "
                + "INNER JOIN cuentas cu ON cl.id_cuenta = cu.id "
                + "WHERE cl.id_cuenta = ?";

        return getCliente(sql, idNumerico);
    }

    /**
     * Obtiene un cliente por nombre de usuario.
     *
     * @param username El nombre de usuario.
     * @return El cliente o null si no existe.
     */
    public Cliente getByUsername(String username) {
        String sql = "SELECT cl.*, cu.username, cu.nombre, cu.contrasenia, cu.correo, cu.telefono "
                + "FROM clientes cl "
                + "INNER JOIN cuentas cu ON cl.id_cuenta = cu.id "
                + "WHERE cu.username = ?";

        return getCliente(sql, username);
    }

    /**
     * Obtiene un cliente por correo.
     *
     * @param mail El correo.
     * @return El cliente o null si no existe.
     */
    public Cliente getByMail(String mail) {
        String sql = "SELECT cl.*, cu.username, cu.nombre, cu.contrasenia, cu.correo, cu.telefono "
                + "FROM clientes cl "
                + "INNER JOIN cuentas cu ON cl.id_cuenta = cu.id "
                + "WHERE cu.correo = ?";

        return getCliente(sql, mail);
    }

    /**
     * Obtiene todos los clientes.
     *
     * @return Lista de todos los clientes.
     */
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        // 1. SQL sin el WHERE para traer toda la tabla
        String sql = "SELECT cl.*, cu.username, cu.nombre, cu.contrasenia, cu.correo, cu.telefono "
                + "FROM clientes cl "
                + "INNER JOIN cuentas cu ON cl.id_cuenta = cu.id";

        try (Connection con = DriverManager.getConnection(Config.URL,
                Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Datos de la cuenta (Padre)
                String username = rs.getString("username");
                String nombre = rs.getString("nombre");
                String contrasenia = rs.getString("contrasenia");
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");

                // Datos del cliente (Hijo)
                int idInt = rs.getInt("id_cuenta");
                String idStr = "Cl" + String.format("%05d", idInt);
                double saldo = rs.getDouble("saldo");
                String membresiaStr = rs.getString("membresia");
                Date fechaSql = rs.getDate("fecha_membresia");

                System.out.println("Cliente encontrado: " + nombre);

                TipoMembresia membresia = TipoMembresia.valueOf(membresiaStr);
                LocalDate fechaMembresia = fechaSql.toLocalDate();
                Cliente cli = new Cliente(saldo, fechaMembresia,
                        membresia, idStr, username,
                        contrasenia, nombre, correo, telefono);
                clientes.add(cli);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los clientes: " + e.getMessage());
        }
        return clientes;
    }

    /*------------------->>U-Update<<-------------------*/
    /**
     * Actualiza los datos de un cliente en la base de datos en base al id.
     *
     * @param cliente el objeto cliente con los datos actualizados.
     * @return verdadero si se lograron a ctualizar los datos.
     */
    public boolean update(Cliente cliente) {
        String sqlCuenta = "UPDATE cuentas SET nombre = ?, contrasenia = ?, correo = ?, telefono = ? WHERE username = ?";
        String sqlCliente = "UPDATE clientes SET saldo = ?, membresia = ?, fecha_membresia = ? WHERE id_cuenta = (SELECT id FROM cuentas WHERE username = ?)";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS)) {
            con.setAutoCommit(false); // Transacción para que se actualicen ambas o ninguna

            try (PreparedStatement psCue = con.prepareStatement(sqlCuenta)) {
                psCue.setString(1, cliente.getNombreCompleto());
                psCue.setString(2, cliente.getContrasenia());
                psCue.setString(3, cliente.getCorreo());
                psCue.setString(4, cliente.getTelefono());
                psCue.setString(5, cliente.getNombreUsuario());
                psCue.executeUpdate();
            }

            try (PreparedStatement psCli = con.prepareStatement(sqlCliente)) {
                psCli.setDouble(1, cliente.getSaldo());
                psCli.setString(2, cliente.getMembresia().toString());
                psCli.setDate(3, Date.valueOf(cliente.getFechaPagoMembresia()));
                psCli.setString(4, cliente.getNombreUsuario());
                psCli.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    /*------------------->>D-Delete<<-------------------*/
    /**
     * Elimina un cliente por nombre de usuario.
     *
     * @param nombreUsuario El nombre de usuario.
     * @return true si se eliminó, false si no existía.
     */
    public boolean remove(String nombreUsuario) {
        String sql = "DELETE FROM cuentas WHERE username = ?";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    /*------------------->>Otros<<-------------------*/
    /**
     * Verifica si existe un cliente con el nombre de usuario dado.
     *
     * @param username El nombre de usuario.
     * @return true si existe, false en caso contrario.
     */
    public boolean existeUsername(String username) {
        String sql = "SELECT COUNT(*) FROM cuentas WHERE username = ?";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar username: " + e.getMessage());
        }
        return false;
    }

    /**
     * Verifica si existe un cliente con el correo dado.
     *
     * @param mail El correo.
     * @return true si existe, false en caso contrario.
     */
    public boolean existeCorreo(String mail) {
        String sql = "SELECT COUNT(*) FROM cuentas WHERE correo = ?";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, mail);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar correo: " + e.getMessage());
        }
        return false;
    }

    /**
     * Calcula la cantidad de cuentas de clientes.
     *
     * @return clatidad de cuentas de clientes.
     */
    public int cantidad() {
        String sql = "SELECT COUNT(*) FROM clientes";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar correo: " + e.getMessage());
        }
        return 0;
    }
}
