package co.edu.udistrital.util;

import co.edu.udistrital.model.dto.ProductoDTO;
import co.edu.udistrital.model.entities.Producto;

/**
 *
 * @author Acer-Pc
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
