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

    private ClienteRepository repositorio;

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
            cliente = repositorio.obtenerPorCorreo(identificador);
        } else {
            cliente = repositorio.obtenerPorUsername(identificador);
        }

        if (cliente == null) {
            return null;
        }

        String passHasheada = SecurityUtil.encriptarSHA256(dto.getContrasenia());

        if (cliente.getContrasenia().equals(passHasheada)) {
            return cliente;
        }

        return null;
    }
}
