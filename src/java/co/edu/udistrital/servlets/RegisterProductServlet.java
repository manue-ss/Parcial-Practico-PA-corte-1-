package co.edu.udistrital.servlets;

import co.edu.udistrital.model.entities.Juego;
import co.edu.udistrital.model.entities.Pelicula;
import co.edu.udistrital.model.repository.JuegoRepository;
import co.edu.udistrital.model.repository.PeliculaRepository;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controlador enfocado en la catalogación de nuevos productos físicos o digitales.
 * Dependiendo del tipo (Película o Juego), recolecta atributos específicos 
 * de la petición y los despacha al repositorio correcto.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
@WebServlet(name = "RegisterProductServlet", urlPatterns = {"/RegisterProductServlet"})
public class RegisterProductServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. Obtener repositorios y VALIDAR que existan
        JuegoRepository jg = (JuegoRepository) getServletContext().getAttribute("juegoRepository");
        PeliculaRepository pe = (PeliculaRepository) getServletContext().getAttribute("peliculaRepository");

        if (jg == null || pe == null) {
            System.err.println("ERROR: Repositorios no encontrados en ServletContext. Revisa tu Listener.");
            response.sendError(500, "Los repositorios no están cargados.");
            return;
        }

        try {
            // 2. Captura de parámetros
            String tipo = request.getParameter("tipoProducto");
            String nombre = request.getParameter("nombreProducto");
            String espe = request.getParameter("especificacion");
            String detalle = request.getParameter("detalleSecundario");

            // Validar que los números no vengan vacíos antes de parsear
            String costoStr = request.getParameter("costoBase");
            String stockStr = request.getParameter("stock");

            if (costoStr == null || stockStr == null) {
                throw new Exception("Los campos de costo y stock son obligatorios.");
            }

            double costoBase = Double.parseDouble(costoStr);
            int stock = Integer.parseInt(stockStr);

            // 3. Lógica de negocio
            if ("JUEGO".equalsIgnoreCase(tipo)) {
                Juego j = new Juego();
                j.setNombreProducto(nombre);
                j.setCostoBase(costoBase);
                j.setStock(stock);
                j.setPlataforma(espe);
                j.setGenero(detalle);
                jg.add(j);
            } else if ("PELICULA".equalsIgnoreCase(tipo)) {
                Pelicula p = new Pelicula();
                p.setNombreProducto(nombre);
                p.setCostoBase(costoBase);
                p.setStock(stock);
                p.setFormato(espe);
                p.setDuracion(detalle);
                pe.add(p);
            }

            // 4. ÉXITO: Redirección física (limpia la URL)
            response.sendRedirect("stock.jsp");

        } catch (Exception e) {
            // 5. ERROR: Imprimir error en consola para que sepas qué pasó
            e.printStackTrace();

            // Guardar el mensaje de error para mostrarlo en el JSP
            request.setAttribute("error", "Error al registrar: " + e.getMessage());

            // IMPORTANTE: Cambia "TU_ARCHIVO.jsp" por el nombre real de tu archivo
            request.getRequestDispatcher("TU_ARCHIVO_FORMULARIO.jsp").forward(request, response);
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
