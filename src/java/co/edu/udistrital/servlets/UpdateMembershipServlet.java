package co.edu.udistrital.servlets;

import co.edu.udistrital.model.dto.ClienteDTO;
import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.repository.ClienteRepository;
import co.edu.udistrital.model.service.GestionCuentaCliente;
import co.edu.udistrital.util.ClienteMapper;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet encargado de gestionar la actualización del nivel de membresía de los
 * clientes, utilizando el Nombre de Usuario como identificador principal.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
@WebServlet(name = "UpdateMembershipServlet", urlPatterns = {"/UpdateMembershipServlet"})
public class UpdateMembershipServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        ClienteRepository repo = (ClienteRepository) getServletContext().getAttribute("clienteRepository");
        GestionCuentaCliente service = new GestionCuentaCliente(repo);

        ClienteDTO clienteSesion = (ClienteDTO) session.getAttribute("usuarioLogueado");
        String nuevaMembresia = request.getParameter("membresia");

        if (nuevaMembresia != null && !nuevaMembresia.isBlank()) {

            if (service.cambiarMembresia(clienteSesion.getId(), nuevaMembresia)) {

                Cliente clienteActualizado = repo.getById(clienteSesion.getId());

                ClienteDTO dtoRefrescado = ClienteMapper.toDTO(clienteActualizado);
                dtoRefrescado.setContrasenia(null);

                session.setAttribute("usuarioLogueado", dtoRefrescado);

                response.sendRedirect("customerProfile.jsp?success=membership_updated");
                return;

            } else {
                // Manejo de error (Saldo insuficiente o error de enum)
                request.setAttribute("errorMembresia", "No se pudo actualizar la membresía. Verifique que tenga saldo suficiente.");
                request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
                return;
            }
        }

        response.sendRedirect("customerProfile.jsp");
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
