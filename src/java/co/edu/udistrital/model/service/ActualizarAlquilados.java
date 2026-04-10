package co.edu.udistrital.model.service;

import co.edu.udistrital.model.entities.Juego;
import co.edu.udistrital.model.entities.Pelicula;
import co.edu.udistrital.model.repository.JuegoRepository;
import co.edu.udistrital.model.repository.PeliculaRepository;

/**
 * Caso de Uso responsable de gestionar el flujo de unidades prestadas. A
 * diferencia de Stock, este servicio modifica el contador de unidades que se
 * encuentran fuera de la tienda.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class ActualizarAlquilados {

    private JuegoRepository juegoRepo;
    private PeliculaRepository peliRepo;

    public ActualizarAlquilados(JuegoRepository juegoRepo, PeliculaRepository peliRepo) {
        this.juegoRepo = juegoRepo;
        this.peliRepo = peliRepo;
    }

    /**
     * Modifica el contador de unidades bajo alquiler.
     *
     * @param idProducto Referencia del producto.
     * @param esDevolucion True si el cliente entrega (resta 1 a alquilados),
     * False si el cliente se lo lleva (suma 1 a alquilados).
     * @return booleano indicando éxito o fracaso en la operación.
     */
    public boolean modificarPrestamo(String idProducto, boolean esDevolucion) {
        if (idProducto == null) {
            return false;
        }

        if (idProducto.startsWith("Jg")) {
            Juego j = juegoRepo.obtenerPorId(idProducto);
            if (j != null) {
                int actual = j.getAlquilados();

                if (esDevolucion) {
                    if (actual <= 0) {
                        return false; // No se puede devolver lo que no se ha prestado
                    }
                    j.setAlquilados(actual - 1);
                } else {
                    // Validar disponibilidad real antes de prestar
                    if (j.getDisponibilidad() <= 0) {
                        return false;
                    }
                    j.setAlquilados(actual + 1);
                }

                juegoRepo.guardar(j);
                return true;
            }
        } else if (idProducto.startsWith("Pe")) {
            Pelicula p = peliRepo.obtenerPorId(idProducto);
            if (p != null) {
                int actual = p.getAlquilados();

                if (esDevolucion) {
                    if (actual <= 0) {
                        return false;
                    }
                    p.setAlquilados(actual - 1);
                } else {
                    if (p.getDisponibilidad() <= 0) {
                        return false;
                    }
                    p.setAlquilados(actual + 1);
                }

                peliRepo.guardar(p);
                return true;
            }
        }
        return false;
    }
}
