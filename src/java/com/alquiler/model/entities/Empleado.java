package com.alquiler.model.entities;

/**
 * Representa a un trabajador del sistema de alquiler. Esta clase extiende de
 * {@link Cuenta} e incluye información laboral y legal específica del personal.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class Empleado extends Cuenta {

    private String dni;
    private String direccion;
    private String cargo;

    /**
     * Constructor completo para crear un nuevo Empleado.
     *
     * @param dni Documento Nacional de Identidad del empleado.
     * @param direccion Domicilio físico de residencia.
     * @param cargo Puesto o rol laboral que desempeña (ej: Administrador,
     * Vendedor).
     * @param id Identificador único asignado por el sistema (ej: EM0001).
     * @param nombreUsuario Nickname para el inicio de sesión.
     * @param contrasenia Hash de la contraseña de seguridad.
     * @param nombreCompleto Nombre real y apellidos del empleado.
     * @param correo Dirección de correo electrónico institucional o personal.
     * @param telefono Número de teléfono de contacto.
     */
    public Empleado(String dni, String direccion, String cargo, String id, String nombreUsuario, String contrasenia, String nombreCompleto, String correo, String telefono) {
        super(id, nombreUsuario, contrasenia, nombreCompleto, correo, telefono);
        this.dni = dni;
        this.direccion = direccion;
        this.cargo = cargo;
    }

    /**
     * Obtiene el DNI del empleado.
     *
     * @return El documento de identidad.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del empleado.
     *
     * @param dni El nuevo número de documento.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene la dirección de residencia del empleado.
     *
     * @return La dirección física.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de residencia.
     *
     * @param direccion La nueva dirección física.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el cargo o puesto que ocupa el empleado.
     *
     * @return El nombre del cargo.
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Establece el cargo o puesto laboral.
     *
     * @param cargo El nuevo cargo asignado.
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
