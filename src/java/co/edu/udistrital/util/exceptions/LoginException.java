package co.edu.udistrital.util.exceptions;

/**
 * Excepción para errores de credenciales (validaciones) al intentar loguearse.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class LoginException extends RuntimeException {
    
    public LoginException(String mensaje) {
        super(mensaje);
    }
}