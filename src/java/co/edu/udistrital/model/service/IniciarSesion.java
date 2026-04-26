package co.edu.udistrital.model.service;

import co.edu.udistrital.model.dto.ClienteDTO;
import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.repository.ClienteRepository;
import co.edu.udistrital.util.SecurityUtil;
import co.edu.udistrital.util.exceptions.LoginException;

/**
 * Caso de Uso responsable de verificar la autenticidad de un Cliente cliente.
 * Se encarga de validar los campos e instanciar la consulta a la BD.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class IniciarSesion {

    private final ClienteRepository repositorio;

    /**
     * Constructor que recibe el repositorio inyectado.
     *
     * @param repositorio El repositorio de clientes encargado de las consultas.
     */
    public IniciarSesion(ClienteRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Ejecuta la comprobación entre el usuario suministrado (User/Mail) y la 
     * contraseña generada por hash.
     *
     * @param dto El objeto con las credenciales que se van a validar.
     * @return El {@link Cliente} instanciado en caso de éxito.
     * @throws LoginException Si el usuario no fue hallado o la contraseña es inválida.
     */
    public Cliente ejecutar(ClienteDTO dto) {

        if (dto.getNombreUsuario() == null || dto.getNombreUsuario().isBlank()
                || dto.getContrasenia() == null || dto.getContrasenia().isBlank()) {
            throw new LoginException("Debes completar todos los campos.");
        }

        Cliente cliente;
        String identificador = dto.getNombreUsuario();

        if (identificador.contains("@")) {
            cliente = repositorio.getByMail(identificador);
        } else {
            cliente = repositorio.getByUsername(identificador);
        }

        String passHasheada = SecurityUtil.SHA256(dto.getContrasenia());
        if (!cliente.validarContrasenia(passHasheada)) {

            throw new LoginException("Usuario o contraseña incorrectos.");
        }

        return cliente;
    }
}
