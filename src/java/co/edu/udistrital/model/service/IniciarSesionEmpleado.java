package co.edu.udistrital.model.service;

import co.edu.udistrital.model.entities.Empleado;
import co.edu.udistrital.model.repository.EmpleadoRepository;
import co.edu.udistrital.util.SecurityUtil;

/**
 * Caso de Uso que gestiona la verificación de credenciales de los
 * miembros del Staff (Administradores y Empleados comunes).
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class IniciarSesionEmpleado {

    private EmpleadoRepository repositorio;

    public IniciarSesionEmpleado(EmpleadoRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Valida el acceso comparando hashes.
     *
     * @param username ID de ingreso del trabajador
     * @param contraseniaTextoPlano Contraseña a validar
     * @return El objeto Empleado en sesión si las credenciales son válidas, null en caso contrario.
     */
    public Empleado autenticar(String username, String contraseniaTextoPlano) {
        Empleado emp = repositorio.getByUsername(username);
        
        if (emp != null) {
            String hashCalculado = SecurityUtil.SHA256(contraseniaTextoPlano);
            if (emp.getContrasenia().equals(hashCalculado)) {
                return emp;
            }
        }
        return null; // Fallido
    }
}
