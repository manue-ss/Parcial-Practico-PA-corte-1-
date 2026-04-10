package co.edu.udistrital.model.entities;

/**
 * Representa un videojuego dentro del sistema de alquiler. Esta clase extiende
 * de {@link Producto} añadiendo atributos específicos como la plataforma
 * (consola) y el género del juego.
 *
 * @since 0.1
 * @author Manuel Salazar
 */
public class Juego extends Producto {

    private String plataforma;
    private String genero;

    /**
     * Constructor por defecto para la clase Juego. Evita errores NullPointer.
     */
    public Juego() {
        super("", "", 0.0, 0, 0);
    }

    /**
     * Constructor para la clase Juego.
     *
     * @param plataforma Consola o sistema para el que está disponible (ej: PS5,
     * Xbox, Switch).
     * @param genero Categoría técnica del juego (ej: RPG, Shooter, Deportes).
     * @param nombreProducto Nombre descriptivo del videojuego.
     * @param id Identificador único del producto.
     * @param costoBase Precio base de alquiler antes de aplicar reglas de
     * plataforma.
     * @param stock Unidades totales físicas o licencias digitales disponibles.
     * @param alquilados Unidades que se encuentran actualmente en préstamo.
     */
    public Juego(String plataforma, String genero, String nombreProducto, String id, double costoBase, int stock, int alquilados) {
        super(nombreProducto, id, costoBase, stock, alquilados);
        this.plataforma = plataforma;
        this.genero = genero;
    }

    /**
     * Calcula el precio final del alquiler basado en las reglas específicas de
     * videojuegos.
     *
     * @return El costo total del alquiler del juego.
     */
    @Override
    public double calcularPrecioFinal() {
        double recargo = 0.0;
        // Lógica de negocio (LSP): los juegos de PS5 tienen un valor adicional
        if (plataforma != null && plataforma.toUpperCase().contains("PS5")) {
            recargo = 2.0;
        }
        return getCostoBase() + recargo;
    }

    /**
     * Obtiene la plataforma o consola del videojuego.
     *
     * @return La plataforma asociada.
     */
    public String getPlataforma() {
        return plataforma;
    }

    /**
     * Define la plataforma o consola del videojuego.
     *
     * @param plataforma Nueva plataforma a asignar.
     */
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    /**
     * Obtiene el género del videojuego.
     *
     * @return El género del juego.
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Define el género del videojuego.
     *
     * @param genero Nuevo género a asignar.
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

}
