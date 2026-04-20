package co.edu.udistrital.util.exceptions;

/**
 * Excepción para errores de lógica de negocio (validaciones).
 */
public class LoginException extends RuntimeException {
    
    public LoginException(String mensaje) {
        super(mensaje);
    }
}