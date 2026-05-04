package co.edu.udistrital.model.repository;

import co.edu.udistrital.model.entities.Empleado;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio encargado de gestionar la persistencia de Empleados y
 * Administradores.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class EmpleadoRepository extends BareRepository<Empleado> {

    public EmpleadoRepository() {
    }

    /*------------------->>C-Create<<-------------------*/
    /**
     * Registra un nuevo empleado en el sistema. Realiza una inserción doble
     * protegida por la lógica de negocio para asegurar la creación del perfil.
     *
     * @param empleado Instancia con los datos de cargo y contacto.
     * @return {@code true} si la operación fue exitosa.
     */
    @Override
    public boolean add(Empleado empleado) {
        String sqlCuenta = "INSERT INTO cuentas (username, nombre, contrasenia, correo, telefono) VALUES (?, ?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO empleados (id_cuenta, dni, direccion, cargo) VALUES (?, ?, ?, ?)";

        try {
            Class.forName(Config.DRIVER);

            try (Connection con = DriverManager.getConnection(Config.URL,
                    Config.USER, Config.PASS)) {
                try (PreparedStatement psC = con.prepareStatement(sqlCuenta,
                        Statement.RETURN_GENERATED_KEYS)) {
                    psC.setString(1, empleado.getNombreUsuario());
                    psC.setString(2, empleado.getNombreCompleto());
                    psC.setString(3, empleado.getContraseniaHash());
                    psC.setString(4, empleado.getCorreo());
                    psC.setString(5, empleado.getTelefono());
                    psC.executeUpdate();

                    ResultSet rs = psC.getGeneratedKeys();
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);

                        try (PreparedStatement psCL = con.prepareStatement(
                                sqlCliente)) {
                            psCL.setInt(1, idGenerado);
                            psCL.setString(2, empleado.getDni());
                            psCL.setString(3, empleado.getDireccion());
                            psCL.setString(4, empleado.getCargo());
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
    private Empleado mapEmpleado(ResultSet rs) throws SQLException {
        // Datos de la cuenta (Padre)
        String username = rs.getString("username");
        String nombre = rs.getString("nombre");
        String contrasenia = rs.getString("contrasenia");
        String correo = rs.getString("correo");
        String telefono = rs.getString("telefono");

        // Datos del empleado (Hijo)
        int idInt = rs.getInt("id_cuenta");
        String dni = rs.getString("dni");
        String direccion = rs.getString("direccion");
        String cargo = rs.getString("cargo");

        String rol = rs.getString("rol");
        String prefijo = rol.equalsIgnoreCase("EMPLEADO") ? "Em" : "Ad";
        String idStr = prefijo + String.format("%05d", idInt);

        System.out.println("Empleado encontrado: " + nombre);

        return new Empleado(dni, direccion,
                cargo, idStr, username,
                contrasenia, nombre, correo, telefono);
    }

    /**
     * Generaliza el proceso de llamado de un empleado segun diferentes
     * parametros.
     *
     * @param sql Sentencia sql para el llamado de los datos.
     * @param param Parametro asociado a la sentecia.
     * @return un obejto cliente segun el parametroo indicado.
     */
    private Empleado getEmpleado(String sql, Object param) {
        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, param);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Empleado emp = mapEmpleado(rs);
                    return emp;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en getById: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Recupera un empleado utilizando su identificador único del sistema.
     *
     * @param idBuscado ID con prefijo 'Em' o 'Ad'.
     * @return Objeto {@link Empleado} o {@code null} si no se encuentra.
     */
    @Override
    public Empleado getById(String idBuscado) {
        int idNumerico = Integer.parseInt(idBuscado.replaceAll("[^0-9]", ""));

        String sql = "SELECT em.*, cu.username, cu.nombre, cu.contrasenia, cu.correo, cu.telefono "
                + "FROM empleados em "
                + "INNER JOIN cuentas cu ON em.id_cuenta = cu.id "
                + "WHERE em.id_cuenta = ?";

        return getEmpleado(sql, idNumerico);
    }

    /**
     * Obtiene un empelado por nombre de usuario.
     *
     * @param username El nombre de usuario.
     * @return El empleado o null si no existe.
     */
    public Empleado getByUsername(String username) {
        String sql = "SELECT em.*, cu.username, cu.nombre, cu.contrasenia, cu.correo, cu.telefono "
                + "FROM empleados em "
                + "INNER JOIN cuentas cu ON em.id_cuenta = cu.id "
                + "WHERE cu.username = ?";

        return getEmpleado(sql, username);
    }

    /**
     * Obtiene un empleado por correo.
     *
     * @param mail El correo.
     * @return El empleado o null si no existe.
     */
    public Empleado getByMail(String mail) {
        String sql = "SELECT em.*, cu.username, cu.nombre, cu.contrasenia, cu.correo, cu.telefono "
                + "FROM empleados em "
                + "INNER JOIN cuentas cu ON em.id_cuenta = cu.id "
                + "WHERE cu.correo = ?";

        return getEmpleado(sql, mail);
    }

    /**
     * Obtiene todos los empleados.
     *
     * @return Lista de todos los empelados.
     */
    @Override
    public List<Empleado> getAll() {
        List<Empleado> empleados = new ArrayList<>();
        // 1. SQL sin el WHERE para traer toda la tabla
        String sql = "SELECT em.*, cu.username, cu.nombre, cu.contrasenia, cu.correo, cu.telefono "
                + "FROM empleados em "
                + "INNER JOIN cuentas cu ON em.id_cuenta = cu.id";

        try (Connection con = DriverManager.getConnection(Config.URL,
                Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Empleado emp = mapEmpleado(rs);
                empleados.add(emp);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los clientes: " + e.getMessage());
        }
        return empleados;
    }

    /*------------------->>U-Update<<-------------------*/
    /**
     * Actualiza la información profesional y de contacto de un empleado.
     *
     * @param empleado Objeto con los datos actualizados.
     * @return {@code true} si se confirmó la transacción.
     */
    public boolean update(Empleado empleado) {
        String sqlCuenta = "UPDATE cuentas SET nombre = ?, correo = ?, telefono = ? WHERE username = ?";
        String sqlCliente = "UPDATE empleados SET dni = ?, direccion = ?, cargo = ? WHERE id_cuenta = ?";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS)) {
            con.setAutoCommit(false); // Transacción para que se actualicen ambas o ninguna

            try (PreparedStatement psCue = con.prepareStatement(sqlCuenta)) {
                psCue.setString(1, empleado.getNombreCompleto());
                psCue.setString(2, empleado.getCorreo());
                psCue.setString(3, empleado.getTelefono());
                psCue.setString(4, empleado.getNombreUsuario());
                psCue.executeUpdate();
            }

            try (PreparedStatement psCli = con.prepareStatement(sqlCliente)) {
                psCli.setString(1, empleado.getDni());
                psCli.setString(2, empleado.getDireccion());
                psCli.setString(3, empleado.getCargo());
                String idStr = empleado.getId();
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
     * Elimina un empleado por nombre de usuario.
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
     * Verifica si existe un empleado con el nombre de usuario dado.
     *
     * @param username El nombre de usuario.
     * @return true si existe, false en caso contrario.
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
     * Verifica si existe un empleado con el correo dado.
     *
     * @param mail El correo.
     * @return true si existe, false en caso contrario.
     */
    public boolean existCorreo(String mail) {
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
     * Calcula la cantidad de cuentas de empleados.
     *
     * @return clatidad de cuentas de empleados.
     */
    @Override
    public int cantidad() {
        String sql = "SELECT COUNT(*) FROM empleados";

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
