package co.edu.udistrital.util;

import co.edu.udistrital.model.dto.ClienteDTO;
import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.enums.TipoMembresia;

/**
 * Utilidad de mapeo bidireccional entre la entidad persistente Cliente y el
 * Objeto de Transferencia de Datos ClienteDTO.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class ClienteMapper {

    public static Cliente toEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();

        cliente.setNombreCompleto(clienteDTO.getNombreCompleto());
        cliente.setNombreUsuario(clienteDTO.getNombreUsuario());
        cliente.setCorreo(clienteDTO.getCorreo());
        cliente.setId(clienteDTO.getId());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setSaldo(clienteDTO.getSaldo());
        TipoMembresia membresia = TipoMembresia.valueOf(clienteDTO.getMembresiaSeleccionada());
        cliente.setMembresia(membresia);
        cliente.setFechaPagoMembresia(clienteDTO.getFechaPagoMembresia());
        cliente.setContrasenia(clienteDTO.getContrasenia());

        return cliente;
    }

    public static ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setNombreCompleto(cliente.getNombreCompleto());
        clienteDTO.setNombreUsuario(cliente.getNombreUsuario());
        clienteDTO.setCorreo(cliente.getCorreo());
        clienteDTO.setId(cliente.getId());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setSaldo(cliente.getSaldo());
        String membresia = cliente.getMembresia().toString();
        clienteDTO.setMembresiaSeleccionada(membresia);
        clienteDTO.setFechaPagoMembresia(cliente.getFechaPagoMembresia());
        clienteDTO.setContrasenia(cliente.getContrasenia());

        return clienteDTO;
    }
}
