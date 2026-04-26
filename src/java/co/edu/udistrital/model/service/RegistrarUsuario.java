package co.edu.udistrital.model.service;

import co.edu.udistrital.model.dto.ClienteDTO;
import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.repository.ClienteRepository;
import co.edu.udistrital.util.ClienteMapper;
import co.edu.udistrital.util.SecurityUtil;
import co.edu.udistrital.util.exceptions.SignupException;

/**
 * Servicio encargado de gestionar el flujo de registro de un Client nuevo en la DB.
 * Aplica validaciones por campo a los nombres, contraseñas seguras y previene
 * duplicidad de usuarios o cuentas vinculadas por email.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class RegistrarUsuario {

    private ClienteRepository repositorio;

    /**
     * Constructor que recibe el repositorio inyectado.
     *
     * @param repositorio El repositorio de clientes para inserción.
     */
    public RegistrarUsuario(ClienteRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Arranca la validación en cadena del DTO y efectúa el intento de inserción.
     *
     * @param cliente El Data Transfer Object capturado del servlet Signup.
     * @throws SignupException Si un dato transgrede el estándar o duplicidad.
     * @throws RuntimeException Si cae un error general de conexión.
     */
    public void ejecutar(ClienteDTO cliente) {
        validar(cliente);

        String passPlana = cliente.getContrasenia();
        String passHasheada = SecurityUtil.SHA256(passPlana);
        Cliente nuevoCliente;

        cliente.setContrasenia(passHasheada);
        cliente.setId("");

        nuevoCliente = ClienteMapper.toEntity(cliente);

        boolean exito = repositorio.add(nuevoCliente);

        if (!exito) {
            // Esta no es una SignupException (error del usuario) 
            // sino un error del sistema
            throw new RuntimeException("No se pudo completar el registro en el servidor.");
        }
    }

    private void validar(ClienteDTO dto) {

        validarNombreCompleto(dto.getNombreCompleto());
        validarNombreUsuario(dto.getNombreUsuario());
        validarCorreo(dto.getCorreo());
        validarTelefono(dto.getTelefono());
        validarContrasenia(dto.getContrasenia());

        // Verificar unicidad
        if (repositorio.existUsername(dto.getNombreUsuario())) {
            throw new SignupException("El nombre de usuario ya existe");
        }
        if (repositorio.existMail(dto.getCorreo())) {
            throw new SignupException("El correo ya está registrado");
        }
    }

    private static void validarNombreCompleto(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new SignupException("El nombre completo no puede estar vacío");
        }
        if (!nombre.trim().contains(" ")) {
            throw new SignupException("Debe ingresar nombre y apellidos completos");
        }
        if (!nombre.matches("[A-Za-z ]+")) {
            throw new SignupException("Solo se permiten letras y espacios para el nombre");
        }
    }

    private static void validarNombreUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            throw new SignupException("El nombre de usuario es obligatorio");
        }
        if (nombreUsuario.length() < 3) {
            throw new SignupException("El nombre de usuario debe tener al menos 3 caracteres");
        }
        if (!nombreUsuario.matches("[A-Za-z0-9_]+")) {
            throw new SignupException("Solo se permiten letras, números y guión bajo");
        }
    }

    private static void validarCorreo(String correo) {
        if (correo == null || correo.isBlank()) {
            throw new SignupException("El correo es obligatorio");
        }
        if (!correo.contains("@") || correo.split("@").length != 2) {
            throw new SignupException("Debe ingresar un correo válido");
        }
    }

    private static void validarTelefono(String telefono) {
        if (telefono == null || telefono.isBlank()) {
            throw new SignupException("El teléfono es obligatorio");
        }
        if (!telefono.matches("[0-9]+")) {
            throw new SignupException("Solo se permiten números en el teléfono");
        }
        if (!telefono.matches("3[0-9]{9}")) {
            throw new SignupException("Debe ingresar número de telefono valido");
        }

    }

    private static void validarContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.isBlank()) {

            throw new SignupException("La contraseña es obligatoria");
        }
        if (contrasenia.length() < 8) {
            throw new SignupException("La contraseña debe tener al menos 8 caracteres");
        }
        if (!contrasenia.matches(".*[A-Z].*")) {
            throw new SignupException("La contraseña debe contener al menos una letra mayúscula");
        }
        if (!contrasenia.matches(".*[a-z].*")) {
            throw new SignupException("La contraseña debe contener al menos una letra minúscula");
        }
        if (!contrasenia.matches(".*[0-9].*")) {
            throw new SignupException("La contraseña debe contener al menos un número");
        }

        if (!contrasenia.matches(".*[!@#$%^&*()].*")) {
            throw new SignupException("La contraseña debe contener al menos un símbolo especial");
        }

    }
}
