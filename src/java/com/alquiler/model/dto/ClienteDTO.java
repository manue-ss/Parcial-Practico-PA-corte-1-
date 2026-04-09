package com.alquiler.model.dto;

import java.time.LocalDate;

/**
 * Objeto de Transferencia de Datos (DTO) para la gestión integral de Clientes.
 *
 * Este objeto se utiliza en dos vías: 1. Entrada: Captura de datos desde el
 * formulario de registro. 2. Salida: Visualización de la información completa
 * en el perfil del usuario, incluyendo datos sensibles como el saldo y estado
 * de membresía.
 *
 * Al ser un DTO, desacopla la lógica interna de la entidad Cliente de la capa
 * de presentación, permitiendo enviar solo la información necesaria al JSP.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class ClienteDTO {

    private String id;
    private String nombreUsuario;
    private String contrasenia;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String membresiaSeleccionada;
    private double saldo;
    private LocalDate fechaPagoMembresia;

    /**
     * Constructor por defecto. Inicializa los atributos de cadena con valores
     * vacíos, el saldo en cero y la fecha de membresía con la fecha actual del
     * sistema para evitar referencias nulas durante la carga inicial de
     * formularios o perfiles.
     */
    public ClienteDTO() {
        this.id = "";
        this.nombreUsuario = "";
        this.contrasenia = "";
        this.nombreCompleto = "";
        this.correo = "";
        this.telefono = "";
        this.membresiaSeleccionada = "";
        this.saldo = 0.0;
        this.fechaPagoMembresia = LocalDate.now();
    }

    /**
     * Constructor completo para visualización de perfil.
     *
     * @param id Identificador único del sistema.
     * @param nombreUsuario Nombre de acceso.
     * @param nombreCompleto Nombre real del cliente.
     * @param correo Email de contacto.
     * @param telefono Teléfono de contacto.
     * @param membresiaSeleccionada Nivel de membresía actual (ej: "SILVER").
     * @param saldo Balance económico disponible.
     * @param fechaPagoMembresia Fecha del último pago o renovación.
     */
    public ClienteDTO(String id, String nombreUsuario, String nombreCompleto, String correo, String telefono, String membresiaSeleccionada, double saldo, LocalDate fechaPagoMembresia) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.membresiaSeleccionada = membresiaSeleccionada;
        this.saldo = saldo;
        this.fechaPagoMembresia = fechaPagoMembresia;
    }

    /**
     * @return El ID único del cliente.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return La contraseña (usualmente vacía o hasheada).
     */
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return Nombre del tipo de membresía.
     */
    public String getMembresiaSeleccionada() {
        return membresiaSeleccionada;
    }

    public void setMembresiaSeleccionada(String membresiaSeleccionada) {
        this.membresiaSeleccionada = membresiaSeleccionada;
    }

    /**
     * @return El saldo disponible para transacciones.
     */
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * @return Fecha de vigencia o último pago de la suscripción.
     */
    public LocalDate getFechaPagoMembresia() {
        return fechaPagoMembresia;
    }

    public void setFechaPagoMembresia(LocalDate fechaPagoMembresia) {
        this.fechaPagoMembresia = fechaPagoMembresia;
    }
}
