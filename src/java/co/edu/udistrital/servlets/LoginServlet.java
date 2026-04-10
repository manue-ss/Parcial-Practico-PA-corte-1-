package co.edu.udistrital.servlets;

import co.edu.udistrital.model.dto.ClienteDTO;
import co.edu.udistrital.model.entities.Alquiler;
import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.entities.Juego;
import co.edu.udistrital.model.entities.Pelicula;
import co.edu.udistrital.model.entities.Producto;
import co.edu.udistrital.model.repository.AlquilerRepository;
import co.edu.udistrital.model.repository.ClienteRepository;
import co.edu.udistrital.model.repository.JuegoRepository;
import co.edu.udistrital.model.repository.PeliculaRepository;
import co.edu.udistrital.model.service.IniciarSesion;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        // 1. OBTENER REPOSITORIOS (Verifica que no sean null en consola)
        ClienteRepository cr = (ClienteRepository) getServletContext().getAttribute("clienteRepository");

        ClienteDTO loginDto = new ClienteDTO();
        loginDto.setNombreUsuario(request.getParameter("username"));
        loginDto.setContrasenia(request.getParameter("password"));

        IniciarSesion iniciarSesion = new IniciarSesion(cr);
        Cliente clienteAutenticado = iniciarSesion.ejecutar(loginDto);

        if (clienteAutenticado != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", clienteAutenticado);

            response.sendRedirect("HomePageServlet");

        } else {
            request.setAttribute("errorLoginMessage", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
        return "Short description";
    }// </editor-fold>

}
