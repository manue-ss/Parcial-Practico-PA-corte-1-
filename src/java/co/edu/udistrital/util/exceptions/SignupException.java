package co.edu.udistrital.util.exceptions;

/**
 * Excepción para errores de captura de datos (validaciones) durante el registro.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class SignupException extends RuntimeException {
    
    public SignupException(String mensaje) {
        super(mensaje);
    }
}