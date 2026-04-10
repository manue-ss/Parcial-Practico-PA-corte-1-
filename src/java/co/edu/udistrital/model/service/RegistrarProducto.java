package co.edu.udistrital.model.service;

import co.edu.udistrital.model.entities.Juego;
import co.edu.udistrital.model.entities.Pelicula;
import co.edu.udistrital.model.repository.JuegoRepository;
import co.edu.udistrital.model.repository.PeliculaRepository;

/**
 * Servicio encargado del alta de productos. Delega la creación física a los
 * repositorios específicos según el tipo evaluado, demostrando el SRP y OCP.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class RegistrarProducto {

    private JuegoRepository juegoRepo;
    private PeliculaRepository peliRepo;

    public RegistrarProducto(JuegoRepository juegoRepo, PeliculaRepository peliRepo) {
        this.juegoRepo = juegoRepo;
        this.peliRepo = peliRepo;
    }

    /**
     * Da de alta un videojuego aplicándole su ID oficial.
     *
     * @param juego Datos recolectados del nuevo juego.
     */
    public void registrarJuego(Juego juego) {
        int qty = juegoRepo.cantidad() + 1;
        juego.setId("Jg" + String.format("%04d", qty));
        juegoRepo.guardar(juego);
    }

    /**
     * Da de alta una película aplicándole su ID oficial.
     *
     * @param peli Datos recolectados de la nueva película.
     */
    public void registrarPelicula(Pelicula peli) {
        int qty = peliRepo.cantidad() + 1;
        peli.setId("Pe" + String.format("%04d", qty));
        peliRepo.guardar(peli);
    }
}
