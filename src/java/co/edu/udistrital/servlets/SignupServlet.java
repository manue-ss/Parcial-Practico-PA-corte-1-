package co.edu.udistrital.servlets;

import co.edu.udistrital.model.dto.ClienteDTO;
import co.edu.udistrital.model.repository.ClienteRepository;
import co.edu.udistrital.model.service.RegistrarUsuario;
import co.edu.udistrital.util.exceptions.SignupException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controlador enfocado en el flujo de registro (Signup).
 * Orquesta la transformación de formulario a DTO y lo envía a la lógica 
 * de negocio para inserción (RegistrarUsuario).
 *
 * @author Manuel Salazar
 * @since 0.2
 */
@WebServlet(name = "SignupServlet", urlPatterns = {"/SignupServlet"})
public class SignupServlet extends HttpServlet {

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

        // 1. Obtener dependencias (centralizado)
        ClienteRepository repo = (ClienteRepository) getServletContext().getAttribute("clienteRepository");

        // 2. Mapeo de datos (Podrías crear un Mapper para esto luego)
        ClienteDTO dto = new ClienteDTO();
        dto.setNombreCompleto(request.getParameter("name"));
        dto.setNombreUsuario(request.getParameter("username"));
        dto.setCorreo(request.getParameter("email"));
        dto.setTelefono(request.getParameter("phone"));
        dto.setContrasenia(request.getParameter("password"));

        // Variables para el flujo de salida
        String mensaje;
        String atributo = "successMessage";

        try {
            RegistrarUsuario registrar = new RegistrarUsuario(repo);
            registrar.ejecutar(dto);
            mensaje = "Usuario registrado exitosamente. Inicia sesión.";
        } catch (SignupException e) {
            mensaje = e.getMessage();
            atributo = "errorSignupMessage";
        } catch (Exception e) {
            System.err.println("ERROR CRÍTICO: " + e.getMessage());
            e.printStackTrace();

            mensaje = "Ocurrió un error inesperado en el servidor. Intente más tarde.";
            atributo = "errorSignupMessage";
        }

        // 4. Único punto de salida para evitar IllegalStateException
        request.setAttribute(atributo, mensaje);
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
