package co.edu.udistrital.model.entities;

/**
 * Representa una película dentro del catálogo de la tienda de alquiler. Esta
 * clase extiende de {@link Producto} incorporando atributos propios de la
 * industria cinematográfica como el formato y la duración.
 *
 * @since 0.1
 * @author Manuel Salazar
 */
public class Pelicula extends Producto {

    private String formato;
    private String duracion;

    /**
     * Constructor por defecto para la clase pelicula. Evita errores de tipo
     * NullPointer
     */
    public Pelicula() {
        super("", "", 0.0, 0, 0);
    }

    /**
     * Constructor para la clase Pelicula.
     *
     * @param formato Tipo de soporte de la película (ej: DVD, Blu-ray,
     * Digital).
     * @param duracion Tiempo de ejecución de la película (ej: "120 min").
     * @param nombreProducto Título oficial de la película.
     * @param id Identificador único asignado al producto.
     * @param costoBase Tarifa estándar de alquiler por día.
     * @param stock Unidades totales disponibles en el inventario físico o
     * digital.
     * @param alquilados Número de copias que se encuentran actualmente
     * prestadas.
     */
    public Pelicula(String formato, String duracion, String nombreProducto, String id, double costoBase, int stock, int alquilados) {
        super(nombreProducto, id, costoBase, stock, alquilados);
        this.formato = formato;
        this.duracion = duracion;
    }

    /**
     * Calcula el precio final del alquiler basado en las reglas específicas de
     * películas.
     *
     * @return El costo total del alquiler de la película aplicando lógica de
     * negocio.
     */
    @Override
    public double calcularPrecioFinal() {
        double recargo = 0.0;
        // Lógica de negocio (LSP): los discos Blu-ray tienen un valor superior
        if (formato != null && formato.toUpperCase().contains("BLU-RAY")) {
            recargo = 1.5;
        }
        return getCostoBase() + recargo;
    }

    /**
     * Obtiene el formato de reproducción de la película.
     *
     * @return El formato (ej: Blu-ray).
     */
    public String getFormato() {
        return formato;
    }

    /**
     * Establece el formato de reproducción de la película.
     *
     * @param formato Nuevo formato para el producto.
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * Obtiene la duración de la película.
     *
     * @return Una cadena que representa la duración.
     */
    public String getDuracion() {
        return duracion;
    }

    /**
     * Establece la duración de la película.
     *
     * @param duracion Nueva duración (ej: "105 min").
     */
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
}
