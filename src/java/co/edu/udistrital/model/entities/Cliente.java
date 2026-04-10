package co.edu.udistrital.model.entities;

import co.edu.udistrital.model.enums.TipoMembresia;
import java.time.LocalDate;

/**
 * Representa a un usuario consumidor del sistema de alquiler. Esta clase
 * extiende de {@link Cuenta} y gestiona el estado financiero, la suscripción y
 * los beneficios del cliente.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class Cliente extends Cuenta {

    private double saldo;
    private LocalDate fechaPagoMembresia;
    private TipoMembresia membresia;

    /**
     * Constructor por defecto. Inicializa los atributos de cadena con valores
     * vacíos, el saldo en cero y la fecha de membresía con la fecha actual del
     * sistema para evitar referencias nulas durante la carga inicial de
     * formularios o perfiles.
     */
    public Cliente() {
        super();
        this.membresia = TipoMembresia.valueOf("NORMAL");
        this.saldo = 0.0;
        this.fechaPagoMembresia = LocalDate.now();
    }

    /**
     * Constructor completo para crear un nuevo Cliente.
     *
     * @param saldo Monto de dinero disponible en la cuenta para realizar
     * alquileres.
     * @param fechaPagoMembresia Fecha en la que se realizó el último pago de
     * suscripción.
     * @param membresia Nivel de beneficios asignado según el Enum
     * {@link TipoMembresia}.
     * @param id Identificador único e inmutable (ej: CL0001).
     * @param nombreUsuario Credencial de acceso única.
     * @param contrasenia Hash de la contraseña del cliente.
     * @param nombreCompleto Nombre y apellidos reales del cliente.
     * @param correo Dirección de correo para notificaciones de renta.
     * @param telefono Número de contacto para seguimiento de devoluciones.
     */
    public Cliente(double saldo, LocalDate fechaPagoMembresia, TipoMembresia membresia, String id, String nombreUsuario, String contrasenia, String nombreCompleto, String correo, String telefono) {
        super(id, nombreUsuario, contrasenia, nombreCompleto, correo, telefono);
        this.saldo = saldo;
        this.fechaPagoMembresia = fechaPagoMembresia;
        this.membresia = membresia;
    }

    /**
     * Obtiene el saldo actual del cliente.
     *
     * @return El balance disponible.
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Actualiza el saldo del cliente.
     *
     * @param saldo El nuevo monto disponible en la cuenta.
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * Obtiene la fecha del último pago o renovación de la membresía. Útil para
     * validar la vigencia de los beneficios.
     *
     * @return La fecha registrada del pago.
     */
    public LocalDate getFechaPagoMembresia() {
        return fechaPagoMembresia;
    }

    /**
     * Establece la fecha de inicio o renovación de la membresía actual.
     *
     * @param fechaPagoMembresia La fecha del movimiento financiero.
     */
    public void setFechaPagoMembresia(LocalDate fechaPagoMembresia) {
        this.fechaPagoMembresia = fechaPagoMembresia;
    }

    /**
     * Obtiene el nivel de membresía actual del cliente.
     *
     * @return Una constante de {@link TipoMembresia}.
     */
    public TipoMembresia getMembresia() {
        return membresia;
    }

    /**
     * Cambia el nivel de membresía del cliente (Upgrade/Downgrade).
     *
     * @param membresia El nuevo tipo de membresía a asignar.
     */
    public void setMembresia(TipoMembresia membresia) {
        this.membresia = membresia;
    }

}
