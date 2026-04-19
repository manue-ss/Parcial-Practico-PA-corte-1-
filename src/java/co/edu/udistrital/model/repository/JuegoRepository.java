package co.edu.udistrital.model.repository;

import co.edu.udistrital.model.entities.Juego;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio enfocado a persistencia y acceso de datos para Juegos.
 * Implementa el principio Abierto/Cerrado (OCP) al dividir el acceso de
 * productos por su clase concreta.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class JuegoRepository {

    public JuegoRepository() {
    }

    /*------------->>C-Create<<---------------*/
    /**
     * Registra un nuevo videojuego en el sistema. Realiza una inserción doble
     * (tabla productos y tabla juegos) dentro de una transacción.
     *
     * @param juego Objeto con la información del juego a persistir.
     * @return {@code true} si la operación fue exitosa y confirmada;
     * {@code false} en caso de error.
     */
    public boolean addJuego(Juego juego) {
        String sqlProducto = "INSERT INTO productos (nombre, costo, stock, tipo_producto) VALUES (?, ?, ?,'JUEGO')";
        String sqlJuego = "INSERT INTO juegos (id_producto, plataforma, genero) VALUES (?, ?, ?)";

        try {
            Class.forName(Config.DRIVER);

            try (Connection con = DriverManager.getConnection(Config.URL,
                    Config.USER, Config.PASS)) {
                con.setAutoCommit(false); // Transacción para que se actualicen ambas o ninguna

                try (PreparedStatement psC = con.prepareStatement(sqlProducto,
                        Statement.RETURN_GENERATED_KEYS)) {
                    psC.setString(1, juego.getNombreProducto());
                    psC.setDouble(2, juego.getCostoBase());
                    psC.setInt(3, juego.getStock());
                    psC.executeUpdate();

                    ResultSet rs = psC.getGeneratedKeys();
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);

                        try (PreparedStatement psCL = con.prepareStatement(
                                sqlJuego)) {
                            psCL.setInt(1, idGenerado);
                            psCL.setString(2, juego.getPlataforma());
                            psCL.setString(3, juego.getGenero());
                            psCL.executeUpdate();

                        }
                    }
                }
                con.commit();
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error en guardado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /*------------->>R-Read<<---------------*/
    /**
     * Mapea el registro actual de un ResultSet a un objeto de tipo Juego.
     *
     * @param rs ResultSet posicionado en la fila a mapear.
     * @return Instancia de {@link Juego} con los datos de la base de datos.
     * @throws SQLException Si ocurre un error al acceder a las columnas del
     * ResultSet.
     */
    private Juego mapJuego(ResultSet rs) throws SQLException {
        // Datos de la cuenta (Padre)
        String nombre = rs.getString("nombre");
        double costo = rs.getDouble("costo");
        int stock = rs.getInt("stock");
        int alquilados = rs.getInt("alquilados");

        // Datos del cliente (Hijo)
        int idInt = rs.getInt("id_producto");
        String idStr = "Jg" + String.format("%05d", idInt);
        String plataforma = rs.getString("plataforma");
        String genero = rs.getString("genero");

        System.out.println("Proucto encontrado: " + nombre);

        return new Juego(plataforma, genero, nombre,
                idStr, costo, stock, alquilados);
    }

    /**
     * Recupera un juego específico mediante una consulta SQL parametrizada.
     *
     * @param sql Sentencia SELECT con un marcador de posición (?).
     * @param param Valor para filtrar la consulta (ID o nombre).
     * @return El objeto {@link Juego} encontrado o {@code null} si no hay
     * coincidencias.
     */
    private Juego getJuego(String sql, Object param) {
        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, param);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Juego juego = mapJuego(rs);
                    return juego;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en getById: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Busca un videojuego por su identificador único.
     *
     * @param idBuscado ID en formato String (ej: "Jg00001").
     * @return Objeto {@link Juego} o {@code null} si no existe.
     */
    public Juego getById(String idBuscado) {
        int idNumerico = Integer.parseInt(idBuscado.replaceAll("[^0-9]", ""));

        String sql = "SELECT jg.*, pr.nombre, pr.costo, pr.stock, pr.alquilados "
                + "FROM juegos jg "
                + "INNER JOIN productos pr ON jg.id_producto = pr.id "
                + "WHERE jg.id_producto = ?";

        return getJuego(sql, idNumerico);
    }

    /**
     * Recupera la lista completa de videojuegos registrados, incluyendo sus
     * datos de producto.
     *
     * @return Una {@link List} de objetos {@link Juego}.
     */
    public List<Juego> getAll() {
        List<Juego> juegos = new ArrayList<>();
        // 1. SQL sin el WHERE para traer toda la tabla
        String sql = "SELECT jg.*, pr.nombre, pr.costo, pr.stock, pr.alquilados "
                + "FROM juegos jg "
                + "INNER JOIN productos pr ON jg.id_producto = pr.id ";

        try (Connection con = DriverManager.getConnection(Config.URL,
                Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Juego jg = mapJuego(rs);

                juegos.add(jg);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los juegos: " + e.getMessage());
        }
        return juegos;
    }

    /*------------->>U-Update<<---------------*/
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
     * Incrementa en una unidad el stock disponible de un producto.
     *
     * @param id Identificador del producto.
     * @return {@code true} si se modificó el registro correctamente.
     */
    public boolean increaseStock(String id) {
        int idNum = Integer.parseInt(id.replaceAll("[^0-9]", ""));
        String sql = "UPDATE productos SET stock = stock + 1 WHERE id = ?";

        return changeData(sql, idNum);
    }

    /**
     * Reduce en una unidad el stock de un producto, siempre que sea mayor a
     * cero.
     *
     * @param id Identificador del producto.
     * @return {@code true} si se realizó la reducción.
     */
    public boolean decreaseStock(String id) {
        int idNum = Integer.parseInt(id.replaceAll("[^0-9]", ""));
        String sql = "UPDATE productos SET stock = stock - 1 WHERE id = ? AND stock > 0";

        return changeData(sql, idNum);
    }

    /**
     * Actualiza la información técnica y comercial de un videojuego existente.
     *
     * @param juego Objeto con los datos actualizados y el ID original.
     * @return {@code true} si la transacción se completó con éxito.
     */
    public boolean update(Juego juego) {
        String sqlProducto = "UPDATE productos SET nombre = ?, costo = ?, stock = ?, alquilados = ? WHERE id = ?";
        String sqlJuego = "UPDATE juegos SET plataforma = ?, genero = ? WHERE id_producto = ?";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS)) {
            con.setAutoCommit(false); // Transacción para que se actualicen ambas o ninguna
            int idNumerico;

            try (PreparedStatement psPr = con.prepareStatement(sqlProducto)) {
                psPr.setString(1, juego.getNombreProducto());
                psPr.setDouble(2, juego.getCostoBase());
                psPr.setInt(3, juego.getStock());
                psPr.setInt(4, juego.getAlquilados());
                idNumerico = Integer.parseInt(juego.getId().replaceAll("[^0-9]", ""));
                psPr.setInt(5, idNumerico);
                psPr.executeUpdate();
            }

            try (PreparedStatement psJg = con.prepareStatement(sqlJuego)) {
                psJg.setString(1, juego.getPlataforma());
                psJg.setString(2, juego.getGenero());
                psJg.setInt(3, idNumerico);
                psJg.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    /*------------->>D-Delete<<---------------*/
    /**
     * Elimina permanentemente el registro de un producto del sistema. Nota:
     * Debido a la integridad referencial, esto eliminará los datos en la tabla
     * juegos.
     *
     * @param id Identificador del producto a remove.
     * @return {@code true} si se eliminó al menos una fila.
     */
    public boolean remove(String id) {
        int idNumerico = Integer.parseInt(id.replaceAll("[^0-9]", ""));
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection con = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idNumerico);

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar juego: " + e.getMessage());
            return false;
        }
    }

    /*------------->>Otros<<---------------*/
    /**
     * Consulta el total de videojuegos registrados en la base de datos.
     *
     * @return Entero con la cuenta total de registros en la tabla juegos.
     */
    public int cantidad() {
        String sql = "SELECT COUNT(*) FROM juegos";

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
