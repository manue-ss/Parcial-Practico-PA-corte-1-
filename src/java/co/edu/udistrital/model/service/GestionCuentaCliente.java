package co.edu.udistrital.model.service;

import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.enums.TipoMembresia;
import co.edu.udistrital.model.repository.ClienteRepository;
import co.edu.udistrital.util.SecurityUtil;

/**
 * Caso de Uso responsable de la gestión financiera y personal de la cuenta.
 * Utiliza el nombre de usuario como identificador principal para la
 * persistencia.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class GestionCuentaCliente {

    private ClienteRepository clienteRepo;

    public GestionCuentaCliente(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    public boolean recargarSaldo(String id, double monto) {
        if (monto <= 0) {
            return false;
        }

        Cliente c = clienteRepo.getById(id);
        if (c != null) {
            c.setSaldo(c.getSaldo() + monto);
            clienteRepo.update(c);
            return true;
        }
        return false;
    }

    /**
     * Permite cambiar el nivel de membresía (subida o bajada) validando que el
     * saldo cubra el costo del nuevo plan.
     */
    public boolean cambiarMembresia(String nombreUsuario, String planStr) {
        Cliente c = clienteRepo.getByUsername(nombreUsuario);

        if (c != null && planStr != null) {
            try {
                TipoMembresia nueva = TipoMembresia.valueOf(planStr.toUpperCase());

                // Regla de Negocio: Solo cambia si tiene saldo suficiente para el costo del plan
                if (c.getSaldo() >= nueva.getCostoMembresia()) {
                    c.setSaldo(c.getSaldo() - nueva.getCostoMembresia());
                    c.setMembresia(nueva);
                    clienteRepo.update(c);
                    return true;
                }
            } catch (IllegalArgumentException e) {
                return false; // El plan enviado no existe en el Enum
            }
        }
        return false;
    }

    public boolean actualizarDatos(String nombreUsuario, String fullName, String phone, String email, String password) {
        Cliente c = clienteRepo.getByUsername(nombreUsuario);

        if (c != null) {
            c.setNombreCompleto(fullName);
            c.setTelefono(phone);
            c.setCorreo(email);

            if (password != null && !password.trim().isEmpty()) {
                c.setContrasenia(SecurityUtil.encriptarSHA256(password));
            }

            clienteRepo.update(c);
            return true;
        }
        return false;
    }
}
