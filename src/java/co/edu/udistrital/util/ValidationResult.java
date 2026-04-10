package co.edu.udistrital.util;

/**
 *
 * @author Acer-Pc
 */
public class ValidationResult {

    private boolean valido;
    private String campo;
    private String mensaje;

    public ValidationResult(boolean valido, String campo, String mensaje) {
        this.valido = valido;
        this.campo = campo;
        this.mensaje = mensaje;
    }

    public boolean isValido() {
        return valido;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public static ValidationResult ok() {
        return new ValidationResult(true, "", "");
    }

    public static ValidationResult error(String campo, String mensaje) {
        return new ValidationResult(false, campo, mensaje);
    }
}
