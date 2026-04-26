/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package co.edu.udistrital.servlets;

import co.edu.udistrital.model.dto.AlquilerDTO;
import co.edu.udistrital.model.dto.ClienteDTO;
import co.edu.udistrital.model.repository.AlquilerRepository;
import co.edu.udistrital.model.repository.JuegoRepository;
import co.edu.udistrital.model.repository.PeliculaRepository;
import co.edu.udistrital.model.service.CatalogoService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 * Controlador de vista para el apartado de "Mis Alquileres".
 * Consulta el catálogo general para traer solo los alquileres no resueltos
 * del cliente actual y los despacha hacia la vista (rentals.jsp).
 *
 * @author Manuel Salazar
 * @since 0.2
 */
@WebServlet(name = "RentalServlet", urlPatterns = {"/RentalServlet"})
public class RentalServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false);

        // 1. Verificación de sesión
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp?error=Sesion expirada");
            return;
        }

        // 2. Obtener dependencias del Contexto (Como lo hicimos en el Home)
        JuegoRepository jr = (JuegoRepository) getServletContext().getAttribute("juegoRepository");
        PeliculaRepository pr = (PeliculaRepository) getServletContext().getAttribute("peliculaRepository");
        AlquilerRepository ar = (AlquilerRepository) getServletContext().getAttribute("alquilerRepository");

        ClienteDTO cliente = (ClienteDTO) session.getAttribute("usuarioLogueado");
        CatalogoService service = new CatalogoService(jr, pr, ar);

        // 3. Obtener solo los alquileres que NO han sido devueltos
        List<AlquilerDTO> vigentes = service.getAlquileresVigentes(cliente.getId());

        // 4. Pasar a la vista
        request.setAttribute("alquileresVigentes", vigentes);
        request.getRequestDispatcher("rentals.jsp").forward(request, response);
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
