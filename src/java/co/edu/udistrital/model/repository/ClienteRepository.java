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
public class ClienteRepository extends BareRepository<Cliente>{

    public ClienteRepository() {
    }

    /*------------------->>C-Create<<-------------------*/
    /**
     * Registra un nuevo cliente realizando una inserción atómica en las tablas
     * 'cuentas' y 'clientes'.
     *
     * @param cliente Objeto con los datos de identidad y financieros.
     * @return {@code true} si se crearon ambos registros exitosamente.
     */
    @Override
    public boolean add(Cliente cliente) {
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
     * Transforma una fila de la base de datos en una instancia de Cliente.
     * Realiza la conversión de tipos SQL (Date) a tipos Java modernos
     * (LocalDate).
     *
     * @param rs ResultSet con la información de la unión de cuentas y clientes.
     * @return Instancia de {@link Cliente}.
     * @throws SQLException Si hay errores en la lectura de columnas.
     */
    private Cliente mapCliente(ResultSet rs) throws SQLException {
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

        return cli;
    }

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

                    Cliente cliente = mapCliente(rs);
                    return cliente;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en getById: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Busca un cliente utilizando su ID único formateado (ej: Cl00001).
     *
     * @param idBuscado Cadena alfanumérica del identificador.
     * @return El {@link Cliente} encontrado o {@code null}.
     */
    @Override
    public Cliente getById(String idBuscado) {
        int idNumerico = Integer.parseInt(idBuscado.replaceAll("[^0-9]", ""));

        String sql = "SELECT cl.*, cu.username, cu.nombre, cu.contrasenia, cu.correo, cu.telefono "
                + "FROM clientes cl "
                + "INNER JOIN cuentas cu ON cl.id_cuenta = cu.id "
                + "WHERE cl.id_cuenta = ?";

        return getCliente(sql, idNumerico);
    }

    /**
     * Recupera un cliente basado en su nombre de usuario (único en la tabla
     * cuentas).
     *
     * @param username El identificador de inicio de sesión.
     * @return El {@link Cliente} correspondiente.
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
    @Override
    public List<Cliente> getAll() {
        List<Cliente> clientes = new ArrayList<>();
        // 1. SQL sin el WHERE para traer toda la tabla
        String sql = "SELECT cl.*, cu.username, cu.nombre, cu.contrasenia, cu.correo, cu.telefono "
                + "FROM clientes cl "
                + "INNER JOIN cuentas cu ON cl.id_cuenta = cu.id";

        try (Connection con = DriverManager.getConnection(Config.URL,
                Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cli = mapCliente(rs);
                clientes.add(cli);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los clientes: " + e.getMessage());
        }
        return clientes;
    }

    /*------------------->>U-Update<<-------------------*/
    /**
     * Actualiza la información de perfil y suscripción del cliente. Utiliza una
     * transacción para asegurar la consistencia entre la cuenta y el perfil del
     * cliente.
     *
     * @param cliente Objeto con los datos modificados.
     * @return {@code true} si la actualización fue completa.
     */
    public boolean update(Cliente cliente) {
        String sqlCuenta = "UPDATE cuentas SET nombre = ?, contrasenia = ?, correo = ?, telefono = ? WHERE username = ?";
        String sqlCliente = "UPDATE clientes SET saldo = ?, membresia = ?, fecha_membresia = ? WHERE id_cuenta = ?";

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
                String idStr = cliente.getId();
                int idNumerico = Integer.parseInt(idStr.replaceAll("[^0-9]", ""));
                psCli.setInt(4, idNumerico);
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
     * Elimina una cuenta de usuario. La integridad referencial (CASCADE) debe
     * encargarse de eliminar el registro asociado en la tabla clientes.
     *
     * @param id Id del cliente a dar de baja.
     * @return {@code true} si el usuario fue eliminado.
     */
    public boolean remove(String id) {
        int idNumerica = Integer.parseInt(id.replaceAll("[^0-9]", ""));
        String sql = "DELETE FROM cuentas WHERE id = ?";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idNumerica);

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    /*------------------->>Otros<<-------------------*/
    /**
     * Verifica la disponibilidad de un nombre de usuario antes de un registro.
     *
     * @param username El nombre a comprobar.
     * @return {@code true} si ya está ocupado.
     */
    public boolean existUsername(String username) {
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
     * Verifica la disponibilidad de un correo electrónico en el sistema. Útil
     * para evitar que múltiples cuentas compartan el mismo email.
     *
     * @param mail El correo a consultar.
     * @return {@code true} si el correo ya está en uso.
     */
    public boolean existMail(String mail) {
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
     * Obtiene el conteo total de registros en la tabla clientes.
     *
     * @return El número total de clientes registrados.
     */
    @Override
    public int cantidad() {
        String sql = "SELECT COUNT(*) FROM clientes";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar cantidad: " + e.getMessage());
        }
        return 0;
    }
}
