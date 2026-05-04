package co.edu.udistrital.servlets;

import co.edu.udistrital.model.dto.ClienteDTO;
import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.repository.*;
import co.edu.udistrital.model.service.ProcesarAlquiler;
import co.edu.udistrital.util.ClienteMapper;
import co.edu.udistrital.util.exceptions.AlquilerException;
import java.io.IOException;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.format.DateTimeParseException;

/**
 * Controlador que procesa el pago y asienta un nuevo contrato de Alquiler.
 * Extrae la información de la sesión y del formulario de rentas para que 
 * el servicio ProcesarAlquiler debite el dinero y actualice inventarios.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
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

        // 2. Obtener Repositorios del Contexto (inyectados por el Listener)
        AlquilerRepository ar = (AlquilerRepository) getServletContext().getAttribute("alquilerRepository");
        ClienteRepository cr = (ClienteRepository) getServletContext().getAttribute("clienteRepository");
        JuegoRepository jr = (JuegoRepository) getServletContext().getAttribute("juegoRepository");
        PeliculaRepository pr = (PeliculaRepository) getServletContext().getAttribute("peliculaRepository");

        // 3. Captura de datos desde la sesión (DTO) y el formulario
        ClienteDTO clienteSession = (ClienteDTO) session.getAttribute("usuarioLogueado");
        String idProducto = request.getParameter("idProducto");
        String fechaDevString = request.getParameter("fechaDevolucion");

        try {
            if (idProducto == null || fechaDevString == null || fechaDevString.isBlank()) {
                throw new AlquilerException("Información de alquiler incompleta.");
            }

            LocalDate fechaDev = LocalDate.parse(fechaDevString);

            ProcesarAlquiler service = new ProcesarAlquiler(ar, cr, jr, pr);

            service.rentar(clienteSession.getId(), idProducto, fechaDev);

            Cliente clienteActualizado = cr.getById(clienteSession.getId());
            ClienteDTO dtoRefrescado = ClienteMapper.toDTO(clienteActualizado);
            dtoRefrescado.setContrasenia(null); // Seguridad
            session.setAttribute("usuarioLogueado", dtoRefrescado);

            response.sendRedirect("RentalServlet?success=true");

        } catch (AlquilerException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("confirmarAlquiler.jsp?idProducto=" + idProducto).forward(request, response);

        } catch (DateTimeParseException e) {
            request.setAttribute("error", "Formato de fecha inválido.");
            request.getRequestDispatcher("confirmarAlquiler.jsp?idProducto=" + idProducto).forward(request, response);

        } catch (Exception e) {
            log("Error crítico en RentProductServlet: ", e);
            request.setAttribute("error", "Error interno del sistema: " + e.getMessage());
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
