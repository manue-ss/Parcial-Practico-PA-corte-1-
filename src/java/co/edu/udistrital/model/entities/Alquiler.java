package co.edu.udistrital.model.entities;

import java.time.LocalDate;

/**
 * Representa un registro de transacción de alquiler en el sistema. Esta clase
 * vincula a un {@link Cliente} con un producto específico y gestiona el ciclo
 * de vida del préstamo, incluyendo fechas de salida, entrega pactada y el
 * estado de vigencia para el archivado en historial.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class Alquiler {

    private String idAlquiler;
    private String idUsuario;
    private String idProducto;
    private LocalDate fechaSalida;
    private LocalDate fechaEntregaPactada;
    private LocalDate fechaDevolucionReal;
    private double costoTotal;
    private boolean vigente;

    /**
     * Constructor para registrar un nuevo alquiler activo. Al crearse, el
     * alquiler se marca automáticamente como vigente y la fecha de devolución
     * real queda inicializada en null.
     *
     * @param idAlquiler Identificador único de la transacción (ej: ALQ-0001).
     * @param idUsuario Identificador del cliente que realiza la renta.
     * @param idProducto Identificador del producto (Película/Juego) rentado.
     * @param fechaSalida Fecha en la que el producto sale del inventario.
     * @param fechaEntregaPactada Fecha límite para devolver el producto sin
     * recargos.
     * @param costoTotal Monto final cobrado al cliente tras aplicar descuentos.
     */
    public Alquiler(String idAlquiler, String idUsuario, String idProducto, LocalDate fechaSalida, LocalDate fechaEntregaPactada, double costoTotal) {
        this.idAlquiler = idAlquiler;
        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
        this.fechaSalida = fechaSalida;
        this.fechaEntregaPactada = fechaEntregaPactada;
        this.costoTotal = costoTotal;
        this.vigente = true;
        this.fechaDevolucionReal = null;
    }

    /**
     * Obtiene el identificador único del alquiler.
     *
     * @return El ID de la transacción.
     */
    public String getIdAlquiler() {
        return idAlquiler;
    }

    /**
     * Establece el identificador único del alquiler.
     *
     * @param idAlquiler El nuevo ID de transacción.
     */
    public void setIdAlquiler(String idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    /**
     * Obtiene el ID del usuario que posee el producto.
     *
     * @return El ID del cliente vinculado.
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Vincula el alquiler a un usuario específico.
     *
     * @param idUsuario El ID del cliente.
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el ID del producto alquilado.
     *
     * @return El ID del producto.
     */
    public String getIdProducto() {
        return idProducto;
    }

    /**
     * Vincula el alquiler a un producto específico.
     *
     * @param idProducto El ID del producto.
     */
    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene la fecha en la que se realizó el alquiler.
     *
     * @return La fecha de salida.
     */
    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    /**
     * Establece la fecha de inicio del alquiler.
     *
     * @param fechaSalida La fecha de salida.
     */
    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    /**
     * Obtiene la fecha límite para la devolución del producto.
     *
     * @return La fecha máxima pactada.
     */
    public LocalDate getFechaEntregaPactada() {
        return fechaEntregaPactada;
    }

    /**
     * Establece la fecha límite para la devolución.
     *
     * @param fechaEntregaPactada La nueva fecha de entrega máxima.
     */
    public void setFechaEntregaPactada(LocalDate fechaEntregaPactada) {
        this.fechaEntregaPactada = fechaEntregaPactada;
    }

    /**
     * Obtiene la fecha en la que el cliente devolvió físicamente el producto.
     *
     * @return La fecha de devolución real, o null si aún no se ha devuelto.
     */
    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    /**
     * Registra la fecha de devolución efectiva. Este método suele llamarse al
     * momento de finalizar la vigencia del alquiler.
     *
     * @param fechaDevolucionReal La fecha de retorno al inventario.
     */
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    /**
     * Obtiene el costo total de la transacción.
     *
     * @return El precio total calculado.
     */
    public double getCostoTotal() {
        return costoTotal;
    }

    /**
     * Establece el costo total del alquiler.
     *
     * @param costoTotal El monto final de la transacción.
     */
    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    /**
     * Indica si el alquiler se encuentra activo (el cliente aún tiene el
     * producto).
     *
     * @return {@code true} si el alquiler es vigente; {@code false} si ha
     * finalizado.
     */
    public boolean isVigente() {
        return vigente;
    }

    /**
     * Actualiza el estado de vigencia del alquiler. Útil para procesos de
     * archivado e historial.
     *
     * @param vigente El nuevo estado de vigencia.
     */
    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }
}
