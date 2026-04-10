package co.edu.udistrital.servlets;

import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.repository.ClienteRepository;
import co.edu.udistrital.model.service.GestionCuentaCliente;
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

        // 1. Verificación de Seguridad (Sesión)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // 2. Obtención de Repositorio y Servicio
        ClienteRepository repo = (ClienteRepository) getServletContext().getAttribute("clienteRepository");
        GestionCuentaCliente service = new GestionCuentaCliente(repo);

        // 3. Obtención de datos actuales y parámetros
        Cliente clienteSesion = (Cliente) session.getAttribute("usuarioLogueado");
        String username = clienteSesion.getNombreUsuario(); // Identificador coherente
        String nuevaMembresia = request.getParameter("membresia");

        // 4. Lógica de actualización basada en Username
        if (nuevaMembresia != null && !nuevaMembresia.isBlank()) {

            // Asumiendo que ajustarás subirMembresia para recibir username o que el ID es el username
            if (service.cambiarMembresia(username, nuevaMembresia)) {

                // REFRESCO DE SESIÓN: Buscamos por el método lógico obtenerPorUsername
                Cliente clienteActualizado = repo.obtenerPorUsername(username);
                session.setAttribute("usuarioLogueado", clienteActualizado);

            } else {
                // Caso de error: saldo insuficiente o fallo en persistencia
                request.setAttribute("errorMembresia", "No se pudo actualizar la membresía. Verifique su saldo.");
                request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
                return;
            }
        }

        // 5. Redirección final exitosa
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
