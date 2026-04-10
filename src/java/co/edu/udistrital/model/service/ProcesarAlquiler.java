package co.edu.udistrital.model.service;

import co.edu.udistrital.model.entities.*;
import co.edu.udistrital.model.repository.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProcesarAlquiler {

    private AlquilerRepository alquilerRepo;
    private ClienteRepository clienteRepo;
    private JuegoRepository juegoRepo;
    private PeliculaRepository peliRepo;
    private ActualizarAlquilados updateAlquilados;

    public ProcesarAlquiler(AlquilerRepository alq, ClienteRepository cli, JuegoRepository jg, PeliculaRepository pe) {
        this.alquilerRepo = alq;
        this.clienteRepo = cli;
        this.juegoRepo = jg;
        this.peliRepo = pe;
        this.updateAlquilados = new ActualizarAlquilados(jg, pe);
    }

    public String rentar(String idClienteOrUser, String idProducto, LocalDate devEstimada) {
        // Buscamos al cliente (idClienteOrUser debe ser la KEY del Map: el username)
        Cliente c = clienteRepo.obtenerPorId(idClienteOrUser);
        if (c == null) {
            return "Error: Cliente no encontrado en el sistema.";
        }

        double costoBaseDia = 0.0;

        // Validación de Producto y Stock
        if (idProducto.startsWith("Jg")) {
            Juego j = juegoRepo.obtenerPorId(idProducto);
            if (j == null || j.getStock() <= 0) {
                return "Producto no disponible o sin existencias.";
            }
            costoBaseDia = j.calcularPrecioFinal();
        } else if (idProducto.startsWith("Pe")) {
            Pelicula p = peliRepo.obtenerPorId(idProducto);
            if (p == null || p.getStock() <= 0) {
                return "Producto no disponible o sin existencias.";
            }
            costoBaseDia = p.calcularPrecioFinal();
        } else {
            return "ID de Producto no reconocido.";
        }

        // Cálculo de tiempo y costos
        long dias = ChronoUnit.DAYS.between(LocalDate.now(), devEstimada);
        if (dias <= 0) {
            dias = 1;
        }

        double costoTotal = dias * costoBaseDia;

        // Aplicación de descuento por membresía
        double descuento = c.getMembresia().getPorcentajeDescuento();
        costoTotal = costoTotal * (1 - descuento);

        // Validación de Saldo
        if (c.getSaldo() < costoTotal) {
            return "Saldo insuficiente. Se requieren $" + String.format("%.2f", costoTotal);
        }

        // --- INICIO DE TRANSACCIÓN ---
        // 1. Modificar Alquiler
        boolean reservado = updateAlquilados.modificarPrestamo(idProducto, false);
        if (!reservado) {
            return "Error crítico al actualizar los alquilados.";
        }

        // 2. Rebajar Saldo y Persistir Cliente
        c.setSaldo(c.getSaldo() - costoTotal);
        clienteRepo.guardar(c);

        // 3. Registrar Alquiler y Persistir
        String idAlquiler = "Alq" + String.format("%04d", alquilerRepo.cantidad() + 1);
        Alquiler alq = new Alquiler(
                idAlquiler,
                c.getNombreUsuario(), // Usamos el nombre de usuario para el registro
                idProducto,
                LocalDate.now(),
                devEstimada,
                costoTotal
        );
        alquilerRepo.guardar(alq);

        return "OK";
    }
}
