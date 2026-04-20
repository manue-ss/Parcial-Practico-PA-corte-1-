package co.edu.udistrital.servlets;

import co.edu.udistrital.model.repository.JuegoRepository;
import co.edu.udistrital.model.repository.PeliculaRepository;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Despachador de peticiones para controlar adición/sustracción manual de stock
 * en el repositorio local.
 *
 * Este controlador permite a los administradores gestionar las existencias de
 * productos de forma individual.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
@WebServlet(name = "UpdateStockServlet", urlPatterns = {"/UpdateStockServlet"})
public class UpdateStockServlet extends HttpServlet {

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
            // 1. Obtención de Repositorios desde el Contexto (Consistencia de inventario)
            JuegoRepository jg = (JuegoRepository) getServletContext().getAttribute("juegoRepository");
            PeliculaRepository pe = (PeliculaRepository) getServletContext().getAttribute("peliculaRepository");

            // 2. Inicialización del Servicio local para Thread-Safety
            // 3. Captura de parámetros
            String idProducto = request.getParameter("idProducto");
            String action = request.getParameter("action"); // "add" o "reduce"
            boolean sumar = "add".equals(action);

            // 4. Ejecución de la lógica
            if (idProducto.startsWith("Jg")) {
                if (sumar) {
                    jg.increaseStock(idProducto);
                } else {
                    jg.decreaseStock(idProducto);
                }
            } else if (idProducto.startsWith("Pl")) {
                if (sumar) {
                    pe.increaseStock(idProducto);
                } else {
                    pe.decreaseStock(idProducto);
                }
            }
            // 5. Redirección al panel de inventario
            response.sendRedirect("stock.jsp");
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
        return "Manual Inventory Stock Update Controller";
    }// </editor-fold>
}
