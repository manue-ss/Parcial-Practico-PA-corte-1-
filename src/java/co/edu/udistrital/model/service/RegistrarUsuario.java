package co.edu.udistrital.model.service;

import co.edu.udistrital.model.dto.ClienteDTO;
import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.repository.ClienteRepository;
import co.edu.udistrital.util.ClienteMapper;
import co.edu.udistrital.util.SecurityUtil;

public class RegistrarUsuario {

    private ClienteRepository repositorio;

    /**
     * Constructor que recibe el repositorio inyectado.
     *
     * @param repositorio El repositorio de clientes.
     */
    public RegistrarUsuario(ClienteRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void ejecutar(ClienteDTO cliente) {
        String passPlana = cliente.getContrasenia();
        String passHasheada = SecurityUtil.encriptarSHA256(passPlana);
        Cliente nuevoCliente;

        cliente.setContrasenia(passHasheada);
        String id = "CL" + String.format("%04d", repositorio.cantidad() + 1);
        cliente.setId(id);

        nuevoCliente = ClienteMapper.toEntity(cliente);

        repositorio.add(nuevoCliente);
    }
}
