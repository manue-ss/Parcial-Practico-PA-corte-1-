package co.edu.udistrital.model.service;

import co.edu.udistrital.model.entities.Alquiler;
import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.entities.Juego;
import co.edu.udistrital.model.entities.Pelicula;
import co.edu.udistrital.model.repository.AlquilerRepository;
import co.edu.udistrital.model.repository.ClienteRepository;
import co.edu.udistrital.model.repository.JuegoRepository;
import co.edu.udistrital.model.repository.PeliculaRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Caso de Uso que gestiona la devolución efectiva de los productos físicos,
 * reponiendo el stock y cerrando la vigencia del contrato de alquiler.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class DevolverAlquiler {

    private AlquilerRepository alquilerRepo;
    private ClienteRepository clienteRepo;
    private JuegoRepository juegoRepo;
    private PeliculaRepository peliRepo;
    private ActualizarStock updateStock;

    public DevolverAlquiler(AlquilerRepository alq, ClienteRepository cli, JuegoRepository jg, PeliculaRepository pe) {
        this.alquilerRepo = alq;
        this.clienteRepo = cli;
        this.juegoRepo = jg;
        this.peliRepo = pe;
        this.updateStock = new ActualizarStock(jg, pe);
    }

    /**
     * Termina el ciclo de vida del alquiler marcado como devuelto el día de hoy
     * y retornando la unidad del catálogo al stock general.
     * 
     * @param idAlquiler Identificador principal de la transacción (AlqXXXX)
     * @return boolean true si fue exitoso, false si fue irreconocible o falló.
     */
    public boolean regresarProducto(String idAlquiler) {
        Alquiler alq = alquilerRepo.obtenerPorId(idAlquiler);
        
        if (alq != null && alq.isVigente()) {
            
            // Lógica de multa si la devolución es tardía
            long diasRetraso = ChronoUnit.DAYS.between(alq.getFechaEntregaPactada(), LocalDate.now());
            if (diasRetraso > 0) {
                double costoBasePro = 0.0;
                if (alq.getIdProducto().startsWith("Jg")) {
                    Juego j = juegoRepo.obtenerPorId(alq.getIdProducto());
                    if (j != null) costoBasePro = j.getCostoBase();
                } else {
                    Pelicula p = peliRepo.obtenerPorId(alq.getIdProducto());
                    if (p != null) costoBasePro = p.getCostoBase();
                }
                
                double multa = diasRetraso * (costoBasePro * 1.5);
                Cliente c = clienteRepo.obtenerPorId(alq.getIdUsuario());
                if (c != null) {
                    c.setSaldo(c.getSaldo() - multa);
                    clienteRepo.guardar(c);
                }
            }
            
            // Reponer stock
            boolean recuperado = updateStock.modificarUnidad(alq.getIdProducto(), true);
            if (recuperado) {
                alq.setVigente(false);
                alq.setFechaDevolucionReal(LocalDate.now());
                alquilerRepo.guardar(alq);
                return true;
            }
        }
        return false;
    }
}
