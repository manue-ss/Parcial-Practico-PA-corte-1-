package co.edu.udistrital.servlets;

import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.repository.*;
import co.edu.udistrital.model.service.ProcesarAlquiler;
import java.io.IOException;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ProcessRentalServlet", urlPatterns = {"/ProcessRentalServlet"})
public class ProcessRentalServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        // 1. Validar Sesión
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // 2. Obtener Repositorios del Contexto (asegurando consistencia de datos)
        AlquilerRepository ar = (AlquilerRepository) getServletContext().getAttribute("alquilerRepository");
        ClienteRepository cr = (ClienteRepository) getServletContext().getAttribute("clienteRepository");
        JuegoRepository jr = (JuegoRepository) getServletContext().getAttribute("juegoRepository");
        PeliculaRepository pr = (PeliculaRepository) getServletContext().getAttribute("peliculaRepository");

        // Inicialización de seguridad por si el Listener no ha corrido
        if (ar == null) ar = new AlquilerRepository();
        if (cr == null) cr = new ClienteRepository();
        if (jr == null) jr = new JuegoRepository();
        if (pr == null) pr = new PeliculaRepository();

        // 3. Captura de datos
        Cliente cliente = (Cliente) session.getAttribute("usuarioLogueado");
        String idProducto = request.getParameter("idProducto");
        String fechaDevString = request.getParameter("fechaDevolucion");

        try {
            LocalDate fechaDev = LocalDate.parse(fechaDevString);
            
            // Instanciar el servicio con los repositorios
            ProcesarAlquiler service = new ProcesarAlquiler(ar, cr, jr, pr);

            // EJECUCIÓN: Pasamos el nombre de usuario porque es la llave del repositorio
            String resultado = service.rentar(cliente.getNombreUsuario(), idProducto, fechaDev);

            if ("OK".equals(resultado)) {
                // Éxito: Redirigir al historial de alquileres
                response.sendRedirect("rentals.jsp");
            } else {
                // Error de negocio (saldo, stock, etc.): Volver con el mensaje
                request.setAttribute("error", resultado);
                request.getRequestDispatcher("confirmarAlquiler.jsp?idProducto=" + idProducto).forward(request, response);
            }

        } catch (Exception e) {
            // Error técnico (fecha mal formateada o nula)
            request.setAttribute("error", "Error en el procesamiento: Verifique las fechas seleccionadas.");
            request.getRequestDispatcher("confirmarAlquiler.jsp?idProducto=" + idProducto).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}