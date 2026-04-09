package com.alquiler.model.entities;

/**
 * Clase abstracta que representa la base de cualquier usuario en el sistema.
 * Contiene la identidad única, credenciales de acceso y datos de contacto
 * compartidos por todos los tipos de cuenta.
 *
 * @since 0.1
 * @author Manuel Salazar
 */
public abstract class Cuenta {

    private String id;
    private String nombreUsuario;
    private String contrasenia;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    
    public Cuenta() {
        this.id = "";
        this.nombreUsuario = "";
        this.contrasenia = "";
        this.nombreCompleto = "";
        this.correo = "";
        this.telefono = "";
    }

    /**
     * Constructor para la clase Cuenta.
     *
     * @param id Identificador único e inmutable de la cuenta.
     * @param nombreUsuario Nombre de acceso único (nickname) del usuario.
     * @param contrasenia Hash de la contraseña de seguridad.
     * @param nombreCompleto Nombre real y apellidos del titular.
     * @param correo Dirección de correo electrónico de contacto.
     * @param telefono Número telefónico de contacto.
     */
    public Cuenta(String id, String nombreUsuario, String contrasenia, String nombreCompleto, String correo, String telefono) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
    }

    /**
     * Obtiene el identificador único de la cuenta.
     *
     * @return El ID de la cuenta.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador único de la cuenta.
     *
     * @param id El nuevo ID único asignado por el sistema.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario (nickname) para el acceso.
     *
     * @return El nombre de usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre de usuario (nickname).
     *
     * @param nombreUsuario El nuevo nombre de usuario único.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene el hash de la contraseña almacenada.
     *
     * @return El hash de la contraseña.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Actualiza la contraseña de la cuenta.
     * <b>Nota de seguridad:</b> El valor proporcionado debe ser un hash seguro.
     *
     * @param contrasenia El nuevo hash de la contraseña.
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * Obtiene el nombre completo del titular.
     *
     * @return El nombre completo.
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Establece el nombre completo del titular de la cuenta.
     *
     * @param nombreCompleto Nombre y apellidos reales.
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Obtiene el correo electrónico de contacto.
     *
     * @return El correo electrónico.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico de contacto.
     *
     * @param correo Nueva dirección de correo electrónico.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el número de teléfono de contacto.
     *
     * @return El número de teléfono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono de contacto.
     *
     * @param telefono Nuevo número telefónico.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
