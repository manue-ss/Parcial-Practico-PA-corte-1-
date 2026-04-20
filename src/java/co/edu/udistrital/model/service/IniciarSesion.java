package co.edu.udistrital.model.service;

import co.edu.udistrital.model.dto.ClienteDTO;
import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.repository.ClienteRepository;
import co.edu.udistrital.util.SecurityUtil;

/**
 *
 * @author Acer-Pc
 */
public class IniciarSesion {

    private final ClienteRepository repositorio;

    /**
     * Constructor que recibe el repositorio inyectado.
     *
     * @param repositorio El repositorio de clientes.
     */
    public IniciarSesion(ClienteRepository repositorio) {
        this.repositorio = repositorio;
    }

    public Cliente ejecutar(ClienteDTO dto) {
        Cliente cliente;
        String identificador = dto.getNombreUsuario();

        if (identificador.contains("@")) {
            cliente = repositorio.getByMail(identificador);
        } else {
            cliente = repositorio.getByUsername(identificador);
        }

        if (cliente != null) {

            String passHasheada = SecurityUtil.SHA256(dto.getContrasenia());

            if (cliente.getContrasenia().equals(passHasheada)) {
                return cliente;
            }
        }
        return null;
    }
}
