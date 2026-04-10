package co.edu.udistrital.model.service;

import co.edu.udistrital.model.entities.Juego;
import co.edu.udistrital.model.entities.Pelicula;
import co.edu.udistrital.model.repository.JuegoRepository;
import co.edu.udistrital.model.repository.PeliculaRepository;

/**
 * Caso de Uso responsable de realizar inserciones y deducciones en las 
 * unidades físicas del catálogo, discerniendo si se trata de un Juego o Película.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class ActualizarStock {

    private JuegoRepository juegoRepo;
    private PeliculaRepository peliRepo;

    public ActualizarStock(JuegoRepository juegoRepo, PeliculaRepository peliRepo) {
        this.juegoRepo = juegoRepo;
        this.peliRepo = peliRepo;
    }

    /**
     * Ajusta el stock de un producto buscando en los repositorios indicados.
     * 
     * @param idProducto Referencia del producto.
     * @param sumar True para añadir 1, False para deducir 1.
     * @return booleano indicando éxito o fracaso en la operación.
     */
    public boolean modificarUnidad(String idProducto, boolean sumar) {
        if (idProducto == null) return false;

        if (idProducto.startsWith("Jg")) {
            Juego j = juegoRepo.obtenerPorId(idProducto);
            if (j != null) {
                int nuevo = sumar ? j.getStock() + 1 : j.getStock() - 1;
                if (nuevo < 0) return false;
                j.setStock(nuevo);
                juegoRepo.guardar(j);
                return true;
            }
        } else if (idProducto.startsWith("Pe")) {
            Pelicula p = peliRepo.obtenerPorId(idProducto);
            if (p != null) {
                int nuevo = sumar ? p.getStock() + 1 : p.getStock() - 1;
                if (nuevo < 0) return false;
                p.setStock(nuevo);
                peliRepo.guardar(p);
                return true;
            }
        }
        return false;
    }
}
