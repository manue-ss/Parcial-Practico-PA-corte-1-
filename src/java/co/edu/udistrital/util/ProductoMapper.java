package co.edu.udistrital.util;

import co.edu.udistrital.model.dto.ProductoDTO;
import co.edu.udistrital.model.entities.Producto;

/**
 * Utilidad de mapeo para transformar la entidad Producto base hacia
 * un DTO simplificado, calculando la disponibilidad real en el proceso.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class ProductoMapper {

    public static ProductoDTO toDTO(Producto producto) {
        ProductoDTO alquilerDTO = new ProductoDTO();

        alquilerDTO.setCostoBase(producto.getCostoBase());
        alquilerDTO.setId(producto.getId());
        alquilerDTO.setDisponibles(producto.getStock() - producto.getAlquilados());
        alquilerDTO.setNombreProducto(producto.getNombreProducto());

        return alquilerDTO;
    }
}
