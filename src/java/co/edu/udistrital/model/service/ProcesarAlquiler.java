package co.edu.udistrital.model.service;

import co.edu.udistrital.model.entities.*;
import co.edu.udistrital.model.repository.*;
import co.edu.udistrital.util.exceptions.AlquilerException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Caso de uso principal responsable de asentar un nuevo alquiler.
 * Gestiona la core-logic validando el saldo, la membresía del cliente,
 * el stock del producto y realizando el cálculo final de la rentabilidad diaria.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class ProcesarAlquiler {

    private AlquilerRepository alquilerRepo;
    private ClienteRepository clienteRepo;
    private JuegoRepository juegoRepo;
    private PeliculaRepository peliRepo;

    /**
     * Construye el inyector de dependencias necesarias para los alquileres.
     *
     * @param alq Repositorio para alquileres.
     * @param cli Repositorio validando información del cliente.
     * @param jg Repositorio de verificación de stock para juegos.
     * @param pe Repositorio de verificación de stock para películas.
     */
    public ProcesarAlquiler(AlquilerRepository alq, ClienteRepository cli, JuegoRepository jg, PeliculaRepository pe) {
        this.alquilerRepo = alq;
        this.clienteRepo = cli;
        this.juegoRepo = jg;
        this.peliRepo = pe;
    }

    /**
     * Ejecuta e intenta guardar una transacción de alquiler descontando el
     * saldo correspondiente al cliente y registrando la fecha de devolución.
     *
     * @param idCliente Identificador único del cliente que solicita.
     * @param idProducto Identificador del producto escogido para alquiler.
     * @param devEstimada Fecha límite calculada para que regrese el producto.
     * @throws AlquilerException Si el saldo es insuficiente o el producto no cuenta con stock.
     */
    public void rentar(String idCliente, String idProducto, LocalDate devEstimada) throws AlquilerException {
        Cliente c = clienteRepo.getById(idCliente);
        if (c == null) {
            throw new AlquilerException("Cliente no encontrado.");
        }

        // 2. Validación de cantidad de alquileres (Regla de Negocio por Membresía)
        // Suponiendo que tienes un método en el repo para contar alquileres activos
        /* if (alquilerRepo.countActivosByCliente(idCliente) >= c.getMembresia().getLimiteAlquiler()) {
        throw new AlquilerException("Has alcanzado el límite de productos para tu membresía " + c.getMembresia());
    }
         */
        double costoBaseDia = 0.0;
        Object producto = null;

        // 3. Validación de Producto y Stock
        if (idProducto.startsWith("Jg")) {
            Juego j = juegoRepo.getById(idProducto);
            if (j == null || j.getStock() <= 0) {
                throw new AlquilerException("Juego no disponible.");
            }
            costoBaseDia = j.calcularPrecioFinal();
            producto = j;
        } else if (idProducto.startsWith("Pe")) {
            Pelicula p = peliRepo.getById(idProducto);
            if (p == null || p.getStock() <= 0) {
                throw new AlquilerException("Película no disponible.");
            }
            costoBaseDia = p.calcularPrecioFinal();
            producto = p;
        }

        long dias = ChronoUnit.DAYS.between(LocalDate.now(), devEstimada);
        if (dias <= 0) {
            dias = 1;
        }

        double costoTotal = dias * costoBaseDia;
        costoTotal *= (1 - c.getMembresia().getPorcentajeDescuento());

        if (c.getSaldo() < costoTotal) {
            throw new AlquilerException("Saldo insuficiente. Necesitas $" + String.format("%.2f", costoTotal));
        }

        c.setSaldo(c.getSaldo() - costoTotal);
        clienteRepo.update(c);

        Alquiler alq = new Alquiler("", idCliente, idProducto, LocalDate.now(), devEstimada, costoTotal);
        alquilerRepo.add(alq);
    }
}
