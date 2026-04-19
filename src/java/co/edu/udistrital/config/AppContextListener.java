package co.edu.udistrital.config;

import co.edu.udistrital.model.repository.ClienteRepository;
import co.edu.udistrital.model.repository.JuegoRepository;
import co.edu.udistrital.model.repository.PeliculaRepository;
import co.edu.udistrital.model.repository.AlquilerRepository; // Importante
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        // 1. Inicializar Repositorio de Clientes
        ClienteRepository clienteRepo = new ClienteRepository();
        context.setAttribute("clienteRepository", clienteRepo);

        // 2. Inicializar Repositorio de Juegos
        JuegoRepository juegoRepo = new JuegoRepository();
        context.setAttribute("juegoRepository", juegoRepo);

        // 3. Inicializar Repositorio de Películas
        PeliculaRepository peliculaRepo = new PeliculaRepository();
        context.setAttribute("peliculaRepository", peliculaRepo);

        // 4. Inicializar Repositorio de Alquileres (Transacciones)
        AlquilerRepository alquilerRepo = new AlquilerRepository();
        context.setAttribute("alquilerRepository", alquilerRepo);

        System.out.println("-------------------------------------------------------");
        System.out.println("✓ RentZone System: Todos los repositorios inicializados.");
        System.out.println(" - Clientes cargados: " + clienteRepo.cantidad());
        System.out.println(" - Alquileres activos: " + alquilerRepo.cantidad());
        System.out.println("-------------------------------------------------------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("✓ Aplicación cerrada. Estado persistido en bases de Datos.");
    }
}
