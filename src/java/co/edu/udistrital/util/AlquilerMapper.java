package co.edu.udistrital.util;

import co.edu.udistrital.model.dto.AlquilerDTO;
import co.edu.udistrital.model.entities.Alquiler;

/**
 *
 * @author Acer-Pc
 */
public class AlquilerMapper {

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
