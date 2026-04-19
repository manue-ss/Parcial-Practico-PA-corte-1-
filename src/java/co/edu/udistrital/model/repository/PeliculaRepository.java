package co.edu.udistrital.model.repository;

import co.edu.udistrital.model.entities.Pelicula;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio enfocado a persistencia y acceso de datos para Películas
 * independientemente.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class PeliculaRepository {

    /**
     * Constructor por defecto.
     */
    public PeliculaRepository() {
    }

    /*-------------->>C-Create<<--------------*/
    /**
     * Añade una pelicula a la base de datos.
     *
     * @param pelicula Objeto de pelicula a almacenar.
     * @return Verdadero si se completo la accion.
     */
    public boolean addPelicula(Pelicula pelicula) {
        String sqlProducto = "INSERT INTO productos (nombre, costo, stock, tipo_producto) VALUES (?, ?, ?,'PELICULA')";
        String sqlPelicula = "INSERT INTO peliculas (id_producto, formato, duracion) VALUES (?, ?, ?)";

        try {

            try (Connection con = DriverManager.getConnection(Config.URL,
                    Config.USER, Config.PASS)) {
                con.setAutoCommit(false); // Transacción para que se actualicen ambas o ninguna

                try (PreparedStatement psC = con.prepareStatement(sqlProducto,
                        Statement.RETURN_GENERATED_KEYS)) {
                    psC.setString(1, pelicula.getNombreProducto());
                    psC.setDouble(2, pelicula.getCostoBase());
                    psC.setInt(3, pelicula.getStock());
                    psC.executeUpdate();

                    ResultSet rs = psC.getGeneratedKeys();
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);

                        try (PreparedStatement psCL = con.prepareStatement(
                                sqlPelicula)) {
                            psCL.setInt(1, idGenerado);
                            psCL.setString(2, pelicula.getFormato());
                            psCL.setString(3, pelicula.getDuracion());
                            psCL.executeUpdate();

                        }
                    }
                }
                con.commit();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error en guardado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /*-------------->>R-Read<<--------------*/
    /**
     * Metodo privado fundamentado en el principio DRY para la contruccion de
     * objetos de tipo Pelicula con datos extraidos de la base de datos.
     *
     * @param rs Objeto ResultSet que contine los datos extraidos de la base de
     * datos.
     * @return El objeto pelicula.
     * @throws SQLException
     */
    private Pelicula mapPelicula(ResultSet rs) throws SQLException {
        // Datos de la cuenta (Padre)
        String nombre = rs.getString("nombre");
        double costo = rs.getDouble("costo");
        int stock = rs.getInt("stock");
        int alquilados = rs.getInt("alquilados");

        // Datos del cliente (Hijo)
        int idInt = rs.getInt("id_producto");
        String idStr = "Pl" + String.format("%05d", idInt);
        String formato = rs.getString("formato");
        String duracion = rs.getString("duracion");

        System.out.println("Producto encontrado: " + nombre);

        return new Pelicula(formato, duracion, nombre,
                idStr, costo, stock, alquilados);
    }

    /**
     * Extrae y construe un objeto de tipo Pelicula de la base de datos.
     *
     * @param sql Sentencia SQL para la extraccion de datos.
     * @param param Parametro de especificacion de la fila a extraer.
     * @return Objeto de tipo Pelicula construido con los datos extraidos.
     */
    private Pelicula getPelicula(String sql, Object param) {
        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, param);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Pelicula pelicula = mapPelicula(rs);
                    return pelicula;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en getById: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Construye un objeto de tipo Pelicula en base a su identificador en la
     * base de datos.
     *
     * @param idBuscado Identificador del objeto a buscar.
     * @return El objeto encontrado. Retorna nulo si no existen registros para
     * esta ID.
     */
    public Pelicula getById(String idBuscado) {
        int idNumerico = Integer.parseInt(idBuscado.replaceAll("[^0-9]", ""));

        String sql = "SELECT pl.*, pr.nombre, pr.costo, pr.stock, pr.alquilados "
                + "FROM peliculas pl "
                + "INNER JOIN productos pr ON pl.id_producto = pr.id "
                + "WHERE pl.id_producto = ?";

        return getPelicula(sql, idNumerico);
    }

    /**
     * Obtiene una lista de todos los productos de tipo Pelicula disponibles.
     *
     * @return Lista de objetos de tipo pelicula.
     */
    public List<Pelicula> getAll() {
        List<Pelicula> peliculas = new ArrayList<>();
        // 1. SQL sin el WHERE para traer toda la tabla
        String sql = "SELECT pl.*, pr.nombre, pr.costo, pr.stock, pr.alquilados "
                + "FROM peliculas pl "
                + "INNER JOIN productos pr ON pl.id_producto = pr.id ";

        try (Connection con = DriverManager.getConnection(Config.URL,
                Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pelicula pel = mapPelicula(rs);

                peliculas.add(pel);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todas las peliculas: " + e.getMessage());
        }
        return peliculas;
    }

    /*-------------->>U-Update<<--------------*/
    /**
     * Ejecuta una sentencia de cambio de datos para una fila especifica en al
     * base de datos.
     *
     * @param sql Sentencia de cambio a ejecutar.
     * @param id Identificador de la fila a cambiar.
     * @return Verdadero si se logró el cambio; falso en caso contrario.
     */
    private boolean changeData(String sql, int id) {
        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar stock: " + e.getMessage());
            return false;
        }
    }

    /**
     * Icrementa el stock de un producto.
     *
     * @param id Identificador del producto a modificar.
     * @return ¿Logró hacer el cambio?
     */
    public boolean increaseStock(String id) {
        int idNum = Integer.parseInt(id.replaceAll("[^0-9]", ""));
        String sql = "UPDATE productos SET stock = stock + 1 WHERE id = ?";

        return changeData(sql, idNum);
    }

    /**
     * Reeduce el stock de un producto.
     *
     * @param id Identificador del producto a modificar.
     * @return ¿Logró hacer el cambio?
     */
    public boolean decreaseStock(String id) {
        int idNum = Integer.parseInt(id.replaceAll("[^0-9]", ""));
        String sql = "UPDATE productos SET stock = stock - 1 WHERE id = ? AND stock > 0";

        return changeData(sql, idNum);
    }

    /**
     * Actualiza la informacion de un producto de tipo Pelicula.
     *
     * @param pelicula Objeto con los datos ya modificados.
     * @return Estado de finalización del proceso.
     */
    public boolean update(Pelicula pelicula) {
        String sqlProducto = "UPDATE productos SET nombre = ?, costo = ?, stock = ?, alquilados = ? WHERE id = ?";
        String sqlJuego = "UPDATE peliculas SET formato = ?, duracion = ? WHERE id_producto = ?";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS)) {
            con.setAutoCommit(false); // Transacción para que se actualicen ambas o ninguna
            int idNumerico;

            try (PreparedStatement psPr = con.prepareStatement(sqlProducto)) {
                psPr.setString(1, pelicula.getNombreProducto());
                psPr.setDouble(2, pelicula.getCostoBase());
                psPr.setInt(3, pelicula.getStock());
                psPr.setInt(4, pelicula.getAlquilados());
                idNumerico = Integer.parseInt(pelicula.getId().replaceAll("[^0-9]", ""));
                psPr.setInt(5, idNumerico);
                psPr.executeUpdate();
            }

            try (PreparedStatement psPl = con.prepareStatement(sqlJuego)) {
                psPl.setString(1, pelicula.getFormato());
                psPl.setString(2, pelicula.getDuracion());
                psPl.setInt(3, idNumerico);
                psPl.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    /*-------------->>D-Delete<<--------------*/
    /**
     * Elimina un producto de tipo Pelicula de la base de datos.
     *
     * @param id Identificador del producto a remove
     * @return Estado de finalizacion del proceso.
     */
    public boolean remove(String id) {
        int idNumerico = Integer.parseInt(id.replaceAll("[^0-9]", ""));
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idNumerico);

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar pelicula: " + e.getMessage());
            return false;
        }
    }

    /*-------------->>Otros<<--------------*/
    /**
     * Cantidad de peliculas distintas en la base de datos.
     *
     * @return Cantidad de elementos encontrados.
     */
    public int cantidad() {
        String sql = "SELECT COUNT(*) FROM peliculas";

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
