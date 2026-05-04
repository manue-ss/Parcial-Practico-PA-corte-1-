package co.edu.udistrital.util.exceptions;

/**
 * Excepción para errores de lógica de negocio (validaciones) relacionados
 * al subsistema de Alquileres.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class AlquilerException extends RuntimeException {
    
    public AlquilerException(String mensaje) {
        super(mensaje);
    }
}