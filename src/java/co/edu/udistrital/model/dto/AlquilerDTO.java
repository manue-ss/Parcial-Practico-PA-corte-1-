package co.edu.udistrital.model.dto;

import java.time.LocalDate;

/**
 * Objeto de Transferencia de Datos (DTO) para la gestión de Alquileres.
 *
 * Estructura utilizada para presentar en las vistas JSP toda la información
 * pertinente a una transacción de alquiler, combinando datos del producto
 * y del cliente para evitar búsquedas adicionales desde la capa de presentación.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class AlquilerDTO {

    private String nombreProducto;
    private String tipoProducto;
    private String idAlquiler;
    private String idCliente;
    private String idProducto;
    private LocalDate fechaAlquiler;
    private LocalDate fechaPactada;
    private double costoTotal;
    private String estado;

    /**
     * Constructor por defecto del DTO de Alquiler.
     *
     * Inicializa todos los atributos estructurados en blanco, nulo o cero
     * previniendo referencias nulas durante la instanciación vacía.
     */
    public AlquilerDTO() {
        this.nombreProducto = "";
        this.tipoProducto = "";
        this.idAlquiler = "";
        this.costoTotal = 0.0;
        this.estado = "";
        this.fechaAlquiler = null;
        this.fechaPactada = null;
        this.idCliente = "";
        this.idProducto = "";
    }

    /**
     * Obtiene el nombre del producto asociado al alquiler.
     * 
     * @return El nombre del producto (ej: Halo Infinito).
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * Establece el nombre del producto asociado.
     *
     * @param nombreProducto Título del producto rentado.
     */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * Obtiene el tipo o categoría del producto (Película/Juego).
     *
     * @return El tipo de producto.
     */
    public String getTipoProducto() {
        return tipoProducto;
    }

    /**
     * Establece el tipo o categoría del producto rentado.
     *
     * @param tipoProducto Categoría textual (ej: Juego).
     */
    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    /**
     * Obtiene el identificador único de la transacción de alquiler.
     *
     * @return El ID del alquiler.
     */
    public String getIdAlquiler() {
        return idAlquiler;
    }

    /**
     * Establece el ID de la transacción de alquiler.
     *
     * @param idAlquiler Identificador (ej: Al0001).
     */
    public void setIdAlquiler(String idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    /**
     * Obtiene el identificador del cliente vinculado.
     *
     * @return El ID del cliente (ej: Cl0010).
     */
    public String getIdCliente() {
        return idCliente;
    }

    /**
     * Establece el identificador del cliente.
     *
     * @param idCliente El ID del usuario asociado.
     */
    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Obtiene el identificador general del producto.
     *
     * @return ID numérico o alfanumérico del producto.
     */
    public String getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador general del producto.
     *
     * @param idProducto El ID registrado del producto.
     */
    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene la fecha de inicio del alquiler.
     *
     * @return Objeto LocalDate indicando cuándo inició la renta.
     */
    public LocalDate getFechaAlquiler() {
        return fechaAlquiler;
    }

    /**
     * Define la fecha de inicio de la renta.
     *
     * @param fechaAlquiler Fecha efectiva de salida.
     */
    public void setFechaAlquiler(LocalDate fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

    /**
     * Obtiene la fecha máxima o pactada para devolución.
     *
     * @return LocalDate del día propuesto para regreso.
     */
    public LocalDate getFechaPactada() {
        return fechaPactada;
    }

    /**
     * Establece la fecha de devolución acordada sin recargos.
     *
     * @param fechaPactada Límite de devolución.
     */
    public void setFechaPactada(LocalDate fechaPactada) {
        this.fechaPactada = fechaPactada;
    }

    /**
     * Obtiene el costo total asociado al alquiler incluyendo recargos nativos.
     *
     * @return El monto final facturado.
     */
    public double getCostoTotal() {
        return costoTotal;
    }

    /**
     * Establece el costo total o facturado.
     *
     * @param costoTotal El costo en moneda local.
     */
    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    /**
     * Obtiene el estado actual del préstamo.
     *
     * @return Estado textul (ej: 'ACTIVO', 'DEVUELTO').
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Define o actualiza el estado de la transacción.
     *
     * @param estado Nuevo nivel de estado lógico.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

}
