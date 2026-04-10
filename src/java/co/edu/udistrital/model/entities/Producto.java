
package co.edu.udistrital.model.entities;

/**
 * Clase abstracta que representa a un producto de una tienda de alquiler.
 * Define los atributos básicos y el comportamiento común para películas,
 * videojuegos y futuros productos.
 *
 * @since 0.1
 * @author Manuel Salazar
 */
public abstract class Producto {

    private String nombreProducto;
    private String id; // Identificador del producto
    private double costoBase;
    private int stock;
    private int alquilados; // Cantidad de unidades del producto alquiladas

    /**
     * Constructor de un objeto abstracto producto.
     *
     * @param nombreProducto Nombre del producto
     * @param id Identificador único del producto
     * @param costoBase Costo monetario base del producto por día
     * @param stock Cantidad de unidades totales del producto en inventario
     * @param alquilados Cantidad de unidades del producto que están actualmente
     * bajo alquiler
     */
    public Producto(String nombreProducto, String id, double costoBase, int stock, int alquilados) {
        this.nombreProducto = nombreProducto;
        this.id = id;
        this.costoBase = costoBase;
        this.stock = stock;
        this.alquilados = alquilados;
    }

    /**
     * Calcula el precio final de alquiler del producto. Este método debe ser
     * implementado por las subclases para aplicar recargos según el formato,
     * consola o tipo de contenido.
     *
     * @return El valor total del alquiler del producto específico.
     */
    public abstract double calcularPrecioFinal();

    /**
     * Calcula las unidades disponibles para nuevos alquileres.
     *
     * @return Unidades disponibles (stock total menos alquilados).
     */
    public int getDisponibilidad() {
        return this.stock - this.alquilados;
    }

    /**
     * Método de retorno del nombre del producto.
     *
     * @return Retorna el nombre del producto.
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * Método de colocación del nombre del producto.
     *
     * @param nombreProducto Nuevo nombre que se le asignará al producto.
     */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * Obtiene el identificador único del producto.
     *
     * @return El ID del producto.
     */
    public String getId() {
        return id;
    }

    /**
     * Asigna un identificador único al producto.
     *
     * @param id Nuevo identificador del producto.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el costo base de alquiler del producto.
     *
     * @return El costo base monetario.
     */
    public double getCostoBase() {
        return costoBase;
    }

    /**
     * Asigna el costo base de alquiler del producto.
     *
     * @param costoBase Nuevo costo base monetario.
     */
    public void setCostoBase(double costoBase) {
        this.costoBase = costoBase;
    }

    /**
     * Obtiene la cantidad total de unidades en stock.
     *
     * @return Cantidad total de unidades.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Actualiza la cantidad total de unidades en stock.
     *
     * @param stock Nueva cantidad de unidades totales.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Obtiene la cantidad de unidades que se encuentran alquiladas actualmente.
     *
     * @return Cantidad de unidades alquiladas.
     */
    public int getAlquilados() {
        return alquilados;
    }

    /**
     * Actualiza la cantidad de unidades alquiladas.
     *
     * @param alquilados Nueva cantidad de unidades en préstamo.
     */
    public void setAlquilados(int alquilados) {
        this.alquilados = alquilados;
    }
}
