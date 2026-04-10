package co.edu.udistrital.servlets;

import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.repository.ClienteRepository;
import co.edu.udistrital.model.service.GestionCuentaCliente;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet encargado de procesar la actualización de los datos personales del
 * cliente (nombre, correo, teléfono) y sincronizar la sesión activa.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
@WebServlet(name = "UpdateProfileServlet", urlPatterns = {"/UpdateProfileServlet"})
public class UpdateProfileServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // 1. Verificación de sesión
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuarioLogueado") == null) {
                response.sendRedirect("index.jsp");
                return;
            }

            // 2. Obtención de dependencias desde el Contexto
            ClienteRepository repository = (ClienteRepository) getServletContext().getAttribute("clienteRepository");
            GestionCuentaCliente service = new GestionCuentaCliente(repository);

            // 3. Captura de parámetros del formulario
            Cliente clienteActual = (Cliente) session.getAttribute("usuarioLogueado");
            String nombre = request.getParameter("nombreCompleto");
            String correo = request.getParameter("correo");
            String telefono = request.getParameter("telefono");

            // 4. Ejecución de la lógica de negocio
            // Pasamos null en la contraseña para indicar que no se desea cambiar en este flujo
            if (service.actualizarDatos(clienteActual.getId(), nombre, telefono, correo, null)) {
                // Sincronizamos el objeto de sesión con los nuevos datos del repositorio
                session.setAttribute("usuarioLogueado", repository.obtenerPorId(clienteActual.getId()));
            }

            // 5. Redirección al perfil para visualizar los cambios
            response.sendRedirect("customerProfile.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Customer Profile Update Controller";
    }// </editor-fold>
}
