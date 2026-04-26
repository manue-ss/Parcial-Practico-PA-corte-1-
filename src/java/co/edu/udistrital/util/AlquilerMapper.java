package co.edu.udistrital.util;

import co.edu.udistrital.model.dto.AlquilerDTO;
import co.edu.udistrital.model.entities.Alquiler;

/**
 * Utilidad de mapeo para transformar instancias físicas de la entidad Alquiler
 * hacia el Objeto de Transferencia de Datos (DTO) diseñado para visualización.
 * Garantiza que la capa web solo exponga la información requerida de manera segura.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class AlquilerMapper {

    /**
     * Convierte un objeto de la entidad Alquiler a un AlquilerDTO.
     * Transporta de forma segura los atributos de estado, fechas e IDs.
     * 
     * @param alquiler El objeto fuente obtenido desde el repositorio.
     * @return El DTO poblado y listo para ser adjuntado como atributo.
     */
    public static AlquilerDTO toDTO(Alquiler alquiler) {
        AlquilerDTO alquilerDTO = new AlquilerDTO();

        alquilerDTO.setCostoTotal(alquiler.getCostoTotal());
        alquilerDTO.setEstado(alquiler.getEstado());
        alquilerDTO.setFechaAlquiler(alquiler.getFechaAlquiler());
        alquilerDTO.setFechaPactada(alquiler.getFechaPactada());
        alquilerDTO.setIdAlquiler(alquiler.getIdAlquiler());
        alquilerDTO.setIdCliente(alquiler.getIdCliente());
        alquilerDTO.setIdProducto(alquiler.getIdProducto());

        return alquilerDTO;
    }
}
