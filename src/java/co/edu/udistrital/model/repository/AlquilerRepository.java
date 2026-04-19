package co.edu.udistrital.model.repository;

import co.edu.udistrital.model.entities.Alquiler;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio de transacciones. Gestiona los metadatos de renta y vincula a
 * usuarios con productos a través de claves foráneas.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class AlquilerRepository {

    /**
     * Constructo por defecto
     */
    public AlquilerRepository() {
    }

    /*--------------->>C-Create<<---------------*/
    /**
     * Añadir un alquiler a la base de datos del sistema.
     *
     * @param alquiler Objeto a registrar en la base de datos.
     * @return verdadero si la adicion fue exitosa, falso en caso contrario.
     */
    public boolean addAlquiler(Alquiler alquiler) {
        String sql = "INSERT INTO alquileres (id_cliente, id_producto, fecha_inicio, fecha_devolucion, costo, estado) VALUES (?, ?, ?, ?, ?,'ACTIVO')";

        try (Connection con = DriverManager.getConnection(Config.URL,
                Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            String idCliente = alquiler.getIdCliente();
            int idClienteNum = Integer.parseInt(idCliente.replaceAll("[^0-9]", ""));
            String idProducto = alquiler.getIdProducto();
            int idProductoNum = Integer.parseInt(idProducto.replaceAll("[^0-9]", ""));
            Date fechaAlquiler = java.sql.Date.valueOf(alquiler.getFechaAlquiler());
            Date fechaPactada = java.sql.Date.valueOf(alquiler.getFechaPactada());

            ps.setInt(1, idClienteNum);
            ps.setInt(2, idProductoNum);
            ps.setDate(3, fechaAlquiler);
            ps.setDate(4, fechaPactada);
            ps.setDouble(5, alquiler.getCostoTotal());
            ps.executeUpdate();

            String sqlPr = "UPDATE productos SET alquilados = alquilados + 1 WHERE id = ?";
            try (PreparedStatement psPr = con.prepareStatement(sqlPr)) {
                psPr.setInt(1, idProductoNum);
                psPr.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Error en guardado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /*--------------->>R-Read<<---------------*/
    /**
     * Metodo privado que mapea o crea un objetoa alquiler con datos extraidos
     * de ua base de datos. Aplica el DRY agrupando codigo reuilizado en mas de
     * uan seccion del codigo.
     *
     * @param rs Objeto RsultSet que contiene los datos de la base de datos.
     * @return El objeto de tipo Alquiler creado.
     * @throws SQLException
     */
    private Alquiler mapAlquiler(ResultSet rs) throws SQLException {

        int idInt = rs.getInt("id");
        int idClienteInt = rs.getInt("id_cliente");
        int idProductoInt = rs.getInt("id_producto");
        Date fechaAlquilerSql = rs.getDate("fecha_inicio");
        Date fechaPactadaSql = rs.getDate("fecha_devolucion");

        String idStr = "Al" + String.format("%05d", idInt);
        String idClienteStr = "Cl" + String.format("%05d", idClienteInt);
        String idProductoStr = "Pr" + String.format("%05d", idProductoInt);
        LocalDate fechaAlquiler = fechaAlquilerSql.toLocalDate();
        LocalDate fechaEntrega = fechaPactadaSql.toLocalDate();
        double costo = rs.getDouble("costo");

        System.out.println("Alquiler encontrado: " + idStr);

        Alquiler alq = new Alquiler(idStr, idClienteStr, idProductoStr, fechaAlquiler,
                fechaEntrega, costo);

        String estado = rs.getString("estado");
        alq.setEstado(estado);

        return alq;
    }

    /**
     * Obtiene los datos de un objeto alquiler desde una base de datos para
     * establecerlos en un onjeto del mismo tipo.
     *
     * @param sql La sentencia SQL para la extraccion de datos.
     * @param param Parametro que apunta a un dato o fila especifico de la base
     * de datos.
     * @return El objero de tipo Alquiler.
     */
    private Alquiler getAlquiler(String sql, Object param) {
        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, param);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Alquiler alq = mapAlquiler(rs);
                    return alq;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en getById: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Extrae un alquiler de la base de datos usando su id asociado.
     *
     * @param id Identificador del alquiler.
     * @return El objeto de tipo Alquiler o null si no extiste.
     */
    public Alquiler getById(String id) {
        int idNumerico = Integer.parseInt(id.replaceAll("[^0-9]", ""));

        String sql = "SELECT * FROM alquileres WHERE id = ?";

        return getAlquiler(sql, idNumerico);
    }

    /**
     * Extrae un listado de todos los alquileres no devueltos de la base de
     * datos.
     *
     * @return Listado de alquileres.
     */
    public List<Alquiler> obtenerTodos() {
        List<Alquiler> alquileres = new ArrayList<>();
        // 1. SQL sin el WHERE para traer toda la tabla
        String sql = "SELECT * FROM alquileres WHERE estado IN ('ACTIVO', 'RETRASADO')";

        try (Connection con = DriverManager.getConnection(Config.URL,
                Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Alquiler alq = mapAlquiler(rs);

                alquileres.add(alq);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener tododos los alquileres: " + e.getMessage());
        }
        return alquileres;
    }

    /**
     *
     * @return La cantidad de alquilres no devueltos en la base de datos.
     */
    public int cantidad() {
        String sql = "SELECT COUNT(*) FROM alquileres WHERE estado IN ('ACTIVO', 'RETRASADO')";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar cantidad: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Construye un arreglo que constiene los alquileres no devueltos de una
     * persona.
     *
     * @param idCliente Identificador único del cliente consultante.
     * @return Lista segregada de alquileres de la persona.
     */
    public List<Alquiler> getByCustomer(String idCliente) {
        List<Alquiler> alquileres = new ArrayList<>();
        // 1. SQL sin el WHERE para traer toda la tabla
        String sql = "SELECT * FROM alquileres WHERE id_cliente = ? AND estado IN ('ACTIVO', 'RETRASADO')";

        try (Connection con = DriverManager.getConnection(Config.URL,
                Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {
            int idNumerico = Integer.parseInt(idCliente.replaceAll("[^0-9]", ""));
            ps.setInt(1, idNumerico);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Alquiler alq = mapAlquiler(rs);

                    alquileres.add(alq);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener tododos los alquileres: " + e.getMessage());
        }
        return alquileres;
    }

    /*--------------->>U-Update<<---------------*/
    /**
     * Retorna un alquiler cambiando su estado en la base de datos.
     *
     * @param id Identiiificador del alquiler a devolver.
     * @return Verdadero si se logró retornar.
     */
    public boolean returnAlquiler(String id) {
        int idNumerico = Integer.parseInt(id.replaceAll("[^0-9]", ""));

        // Esta consulta actualiza AMBAS tablas al mismo tiempo
        String sql = "UPDATE alquileres alq "
                + "INNER JOIN productos pr ON alq.id_producto = pr.id "
                + "SET alq.estado = 'DEVUELTO', "
                + "    pr.alquilados = pr.alquilados - 1 "
                + "WHERE alq.id = ?";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER,
                Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {

            // Al ser una sola sentencia SQL, MariaDB garantiza que es atómica.
            // Si falla la actualización del stock, no se cambia el estado del alquiler.
            ps.setInt(1, idNumerico);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error en devolución con JOIN: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /*--------------->>D-Delete<<---------------*/
    /**
     * Elemina un alquiler de la base de datos.
     *
     * Se recomienda no usar. Se recomienda establecer el estado del alquiler
     * como devuelto.
     *
     * @param id Identificador del alquiler a eliminar.
     * @return Verdadero si se elimino, falso en caso contrario. Ya sea por
     * inexistencia del alquiler o un fallo en la ejcucion del metodo.
     */
    /*--------------->>D-Delete<<---------------*/
    /**
     * Elemina un alquiler de la base de datos.
     *
     * Se recomienda no usar. Se recomienda establecer el estado del alquiler
     * como devuelto.
     *
     * @param id Identificador del alquiler a remove.
     * @return Verdadero si se elimino, falso en caso contrario. Ya sea por
     * inexistencia del alquiler o un fallo en la ejcucion del metodo.
     */
    public boolean remove(String id) {
        int idNumerico = Integer.parseInt(id.replaceAll("[^0-9]", ""));
        String sql = "DELETE FROM alquileres WHERE id = ?";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idNumerico);

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar alquiler: " + e.getMessage());
            return false;
        }
    }

    /*--------------->>Otros<<---------------*/
}
