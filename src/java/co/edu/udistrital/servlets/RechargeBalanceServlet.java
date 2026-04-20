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
 * Endpoint para recargar fondos localmente a la cuenta de usuario. Sigue la
 * estructura estandarizada con processRequest.
 *
 * @author Manuel Salazar
 */
@WebServlet(name = "RechargeBalanceServlet", urlPatterns = {"/RechargeBalanceServlet"})
public class RechargeBalanceServlet extends HttpServlet {

    /**
     * Procesa las peticiones para recargar el saldo del cliente.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // 1. Verificar sesión
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuarioLogueado") == null) {
                response.sendRedirect("index.jsp");
                return;
            }

            // 2. Obtener dependencias y datos
            ClienteRepository repositorio = (ClienteRepository) getServletContext().getAttribute("clienteRepository");
            GestionCuentaCliente service = new GestionCuentaCliente(repositorio);

            Cliente cliente = (Cliente) session.getAttribute("usuarioLogueado");

            try {
                double monto = Double.parseDouble(request.getParameter("monto"));

                // 3. Ejecutar lógica de negocio
                if (service.recargarSaldo(cliente.getId(), monto)) {
                    Cliente clienteActual = repositorio.getById(cliente.getId());
                    session.setAttribute("usuarioLogueado", clienteActual);
                }

                response.sendRedirect("customerProfile.jsp");

            } catch (NumberFormatException | NullPointerException e) {
                // Manejo de error si el monto no es válido
                request.setAttribute("errorMessage", "Monto de recarga inválido");
                request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    /**
     * Handles the HTTP <code>GET</code> method.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet encargado de la recarga de saldo del cliente";
    }
    // </editor-fold>
}
