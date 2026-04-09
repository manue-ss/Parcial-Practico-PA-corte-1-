package com.alquiler.config;

import com.alquiler.model.repository.ClienteRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Listener que se ejecuta al iniciar la aplicación. Carga el repositorio de
 * clientes (HashMap desde JSON) en memoria, lo guarda en el ServletContext para
 * acceso global.
 *
 * @author Acer-Pc
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Se ejecuta cuando la aplicación se inicia
        ServletContext context = sce.getServletContext();

        // Crear e inicializar el repositorio (carga JSON en HashMap)
        ClienteRepository repositorio = new ClienteRepository();

        // Guardar en el ServletContext para acceso global
        context.setAttribute("clienteRepository", repositorio);

        System.out.println("✓ ClienteRepository inicializado al startup. Clientes cargados: "
                + repositorio.cantidadClientes());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Se ejecuta cuando la aplicación se apaga
        System.out.println("✓ Aplicación cerrada. Cambios guardados en clientes.json");
    }
}
