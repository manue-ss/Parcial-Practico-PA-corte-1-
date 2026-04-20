package co.edu.udistrital.util.exceptions;

/**
 * Excepción para errores de lógica de negocio (validaciones).
 */
public class SignupException extends RuntimeException {
    
    public SignupException(String mensaje) {
        super(mensaje);
    }
}