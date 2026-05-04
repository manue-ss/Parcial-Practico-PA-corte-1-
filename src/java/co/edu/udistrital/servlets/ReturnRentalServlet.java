package co.edu.udistrital.servlets;

import co.edu.udistrital.model.repository.AlquilerRepository;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet llamado al hacer hover rojo ('X') en el rental. Cancela una
 * transacción abierta y repone las unidades a tienda.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
@WebServlet(name = "ReturnRentalServlet", urlPatterns = {"/ReturnRentalServlet"})
public class ReturnRentalServlet extends HttpServlet {

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
            // 1. Verificación de Seguridad (Sesión)
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuarioLogueado") == null) {
                response.sendRedirect("index.jsp");
                return;
            }

            // 2. Obtención de Repositorios desde el Contexto de la aplicación
            AlquilerRepository ar = (AlquilerRepository) getServletContext().getAttribute("alquilerRepository");


            // 4. Ejecución de la devolución
            String idAlquiler = request.getParameter("idAlquiler");
            if (idAlquiler != null && !idAlquiler.isBlank()) {
                ar.returnAlquiler(idAlquiler);
            }

            // 5. Redirección al historial de alquileres actualizado
            response.sendRedirect("rentals.jsp");
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
        return "Rental Return and Stock Replenishment Agent";
    }// </editor-fold>
}
