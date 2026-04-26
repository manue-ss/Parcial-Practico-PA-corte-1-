package co.edu.udistrital.model.service;

import co.edu.udistrital.model.dto.ClienteDTO;
import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.enums.TipoMembresia;
import co.edu.udistrital.model.repository.ClienteRepository;

/**
 * Caso de Uso responsable de la gestión financiera y personal de la cuenta.
 * Utiliza el identificador principal para la persistencia y actualización
 * de los datos sensibles y control de saldos del cliente.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class GestionCuentaCliente {

    private final ClienteRepository clienteRepo;

    /**
     * Constructor inyectando el repositorio de acceso a datos de clientes.
     *
     * @param clienteRepo Repositorio gestionado que conecta con MariaDB.
     */
    public GestionCuentaCliente(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    /**
     * Incrementa el saldo disponible en la billetera virtual del cliente.
     *
     * @param id El identificador único del cliente (Ej: Cl001).
     * @param monto La cantidad monetaria real a sumar a su saldo existente.
     * @return {@code true} si la operación modificó las filas con éxito, {@code false} de lo contrario.
     */
    public boolean recargarSaldo(String id, double monto) {
        if (monto <= 0) {
            return false;
        }

        Cliente c = clienteRepo.getById(id);
        if (c == null) {
            return false;
        }

        c.setSaldo(c.getSaldo() + monto);
        clienteRepo.update(c);
        return true;
    }

    /**
     * Gestiona el cambio de nivel de beneficios, efectuando el cobro 
     * en el saldo según el Enum correspondiente, o validando si alcanza.
     *
     * @param id El identificador único del cliente.
     * @param planStr El nombre del plan deseado (e.g., NORMAL, SILVER, GOLD).
     * @return {@code true} si se aplicó de manera exitosa el Upgrade/Downgrade.
     */
    public boolean cambiarMembresia(String id, String planStr) {
        Cliente c = clienteRepo.getById(id); // Uso de ID preferido
        if (c == null || planStr == null) {
            return false;
        }

        try {
            TipoMembresia nueva = TipoMembresia.valueOf(planStr.toUpperCase());

            if (c.getMembresia() == nueva) {
                return true;
            }

            if (c.getSaldo() >= nueva.getCostoMembresia()) {
                c.setSaldo(c.getSaldo() - nueva.getCostoMembresia());
                c.setMembresia(nueva);
                clienteRepo.update(c);
                return true;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }

    /**
     * Sincroniza desde el DTO los datos modificables del perfil de cliente
     * (únicamente el Nombre, Teléfono o Correo).
     *
     * @param dto Contiene la información capturada de los formularios.
     * @return {@code true} si el perfil fue salvado exitosamente.
     */
    public boolean actualizarDatos(ClienteDTO dto) {
        Cliente c = clienteRepo.getById(dto.getId());

        if (c != null) {

            c.setNombreCompleto(dto.getNombreCompleto());
            c.setTelefono(dto.getTelefono());
            c.setCorreo(dto.getCorreo());

            clienteRepo.update(c);
            return true;
        }
        return false;
    }
}
