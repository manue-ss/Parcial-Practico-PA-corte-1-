package com.alquiler.model.enums;

/**
 * Define los niveles de membresía disponibles para los clientes. Cada nivel
 * contiene sus propias reglas de negocio: límites de productos, costos
 * mensuales y porcentajes de descuento.
 *
 * @author Manuel Salazar
 */
public enum TipoMembresia {

    /**
     * Membresía básica: - Límite: 2 productos. - Costo: 0.0 (Gratis). -
     * Descuento: 0%.
     */
    NORMAL(2, 0.0, 0.0),
    /**
     * Membresía intermedia: - Límite: 5 productos. - Costo: 15.0. - Descuento:
     * 10%.
     */
    SILVER(5, 15.0, 0.10),
    /**
     * Membresía premium: - Límite: 10 productos. - Costo: 30.0. - Descuento:
     * 20%.
     */
    GOLD(10, 30.0, 0.20);

    private final int limiteAlquiler;
    private final double costoMembresia;
    private final double porcentajeDescuento;

    /**
     * Constructor privado del Enum. Java lo invoca automáticamente al
     * inicializar las constantes (NORMAL, SILVER, GOLD).
     *
     * @param limiteAlquiler Cantidad máxima de ítems permitidos.
     * @param costoMembresia Precio mensual del nivel de suscripción.
     * @param porcentajeDescuento Factor de descuento (0.0 a 1.0).
     */
    private TipoMembresia(int limiteAlquiler, double costoMembresia, double porcentajeDescuento) {
        this.limiteAlquiler = limiteAlquiler;
        this.costoMembresia = costoMembresia;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    /**
     * @return El límite máximo de productos que el cliente puede alquilar.
     */
    public int getLimiteAlquiler() {
        return limiteAlquiler;
    }

    /**
     * @return El costo mensual de la membresía.
     */
    public double getCostoMembresia() {
        return costoMembresia;
    }

    /**
     * @return El porcentaje de descuento aplicable (ej: 0.1 para 10%).
     */
    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }
}
