package co.edu.udistrital.model.dto;

/**
 * Objeto de Transferencia de Datos (DTO) para los Productos.
 *
 * Se encarga de transportar la información consolidada de los productos
 * (como Juegos y Películas) hacia la capa de presentación de forma unificada.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class ProductoDTO {

    private String nombreProducto;
    private String categoria;
    private String plataforma;
    private String detalle;
    private String id;
    private double costoBase;
    private int disponibles;

    /**
     * Constructor por defecto del DTO de Producto.
     * Inicializa los campos de texto como cadenas vacías y los valores numéricos en cero,
     * previniendo referencias nulas en las vistas JSP.
     */
    public ProductoDTO() {
        nombreProducto = "";
        categoria = "";
        plataforma = "";
        detalle = "";
        id = "";
        costoBase = 0.0;
        disponibles = 0;
    }

    /**
     * Obtiene el nombre o título del producto.
     *
     * @return El nombre descriptivo del producto.
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * Define el nombre o título del producto.
     *
     * @param nombreProducto Nuevo nombre del producto.
     */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * Obtiene la categoría general del producto (ej: Película, Juego).
     *
     * @return La categoría asociada al producto.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Define la categoría del producto.
     *
     * @param categoria Nueva categoría (ej: "Digital", "Física").
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtiene la plataforma asociada si el producto es un juego (ej: PS5, PC).
     *
     * @return El nombre de la plataforma.
     */
    public String getPlataforma() {
        return plataforma;
    }

    /**
     * Define la plataforma o consola del producto.
     *
     * @param plataforma Nueva plataforma a asignar.
     */
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    /**
     * Obtiene los detalles específicos adicionales (ej: duración, género).
     *
     * @return El detalle o descripción breve.
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * Define un detalle o especificación adicional para el producto.
     *
     * @param detalle Nueva descripción breve o detalle.
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    /**
     * Obtiene el identificador único del producto.
     *
     * @return El ID alfanumérico del producto.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el código identificador del producto.
     *
     * @param id Nuevo ID único.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el costo o base tarifaria de alquiler.
     *
     * @return El costo base calculado en formato decimal.
     */
    public double getCostoBase() {
        return costoBase;
    }

    /**
     * Establece la tarifa de alquiler base.
     *
     * @param costoBase Nuevo precio base de alquiler por periodo.
     */
    public void setCostoBase(double costoBase) {
        this.costoBase = costoBase;
    }

    /**
     * Obtiene la cantidad de copias o licencias listas para rentar.
     *
     * @return Unidades netamente disponibles.
     */
    public int getDisponibles() {
        return disponibles;
    }

    /**
     * Define el inventario libre actual.
     *
     * @param disponibles Nueva cantidad de unidades despachables.
     */
    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }

}
