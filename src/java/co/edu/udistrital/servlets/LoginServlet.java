package co.edu.udistrital.servlets;

import co.edu.udistrital.model.dto.ClienteDTO;
import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.repository.ClienteRepository;
import co.edu.udistrital.model.service.IniciarSesion;
import co.edu.udistrital.util.ClienteMapper;
import co.edu.udistrital.util.exceptions.LoginException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador encargado de gestionar el inicio de sesión de los clientes.
 * Procesa las credenciales de acceso, valida la identidad mediante el servicio
 * de autenticación y gestiona la creación de la sesión de usuario.
 *
 * @author Manuel Salazar
 * @version 1.0
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
        String mensaje = "";

        try {

            String user = request.getParameter("username");
            String pass = request.getParameter("password");

            ClienteRepository cr = (ClienteRepository) getServletContext().getAttribute("clienteRepository");
            if (cr == null) {
                throw new RuntimeException("Repositorio no encontrado");
            }

            ClienteDTO loginDto = new ClienteDTO();
            loginDto.setNombreUsuario(user != null ? user.trim() : "");
            loginDto.setContrasenia(pass);

            IniciarSesion service = new IniciarSesion(cr);
            Cliente cliente = service.ejecutar(loginDto);

            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            session = request.getSession(true);

            ClienteDTO sessionDto = ClienteMapper.toDTO(cliente);
            sessionDto.setContrasenia(null);
            session.setAttribute("usuarioLogueado", sessionDto);
            response.sendRedirect("HomePageServlet");

        } catch (LoginException e) {
            mensaje = e.getMessage();

        } catch (IOException | RuntimeException e) {
            log("Error en Login: ", e);
            mensaje = "Error técnico en el sistema";
        }

        request.setAttribute("errorLoginMessage", mensaje);
        request.getRequestDispatcher("index.jsp").forward(request, response);
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
        return "Short description";
    }// </editor-fold>

}
