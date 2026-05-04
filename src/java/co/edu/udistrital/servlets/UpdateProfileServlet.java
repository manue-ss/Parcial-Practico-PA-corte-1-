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
 * Servlet encargado de procesar la actualización de los datos personales del
 * cliente (nombre, correo, teléfono) y sincronizar la sesión activa.
 *
 * @author Manuel Salazar
 * @since 0.2
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
        HttpSession session = request.getSession(false);

        // 1. Verificación de seguridad
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        ClienteRepository repository = (ClienteRepository) getServletContext().getAttribute("clienteRepository");
        GestionCuentaCliente service = new GestionCuentaCliente(repository);

        ClienteDTO clienteSession = (ClienteDTO) session.getAttribute("usuarioLogueado");

        try {
            ClienteDTO datosNuevos = new ClienteDTO();
            datosNuevos.setId(clienteSession.getId());
            datosNuevos.setNombreCompleto(request.getParameter("nombreCompleto"));
            datosNuevos.setCorreo(request.getParameter("correo"));
            datosNuevos.setTelefono(request.getParameter("telefono"));


            if (service.actualizarDatos(datosNuevos)) {
                Cliente actualizado = repository.getById(clienteSession.getId());

                ClienteDTO dtoRefrescado = ClienteMapper.toDTO(actualizado);
                dtoRefrescado.setContrasenia(null);

                session.setAttribute("usuarioLogueado", dtoRefrescado);

                response.sendRedirect("customerProfile.jsp?success=updated");
            } else {
                response.sendRedirect("customerProfile.jsp?error=update_failed");
            }

        } catch (Exception e) {
            log("Error actualizando perfil: ", e);
            response.sendRedirect("customerProfile.jsp?error=internal_error");
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
