package co.edu.udistrital.servlets;

import co.edu.udistrital.model.dto.ClienteDTO;
import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.repository.ClienteRepository;
import co.edu.udistrital.model.service.GestionCuentaCliente;
import co.edu.udistrital.util.ClienteMapper;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Endpoint para recargar fondos localmente a la cuenta de usuario. Delega 
 * al Caso de Uso la inserción de saldo simulando una pasarela de pago.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
@WebServlet(name = "RechargeBalanceServlet", urlPatterns = {"/RechargeBalanceServlet"})
public class RechargeBalanceServlet extends HttpServlet {

    /**
     * Procesa las peticiones para recargar el saldo del cliente.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp?error=Sesion expirada");
            return;
        }

        ClienteRepository repositorio = (ClienteRepository) getServletContext().getAttribute("clienteRepository");
        GestionCuentaCliente service = new GestionCuentaCliente(repositorio);

        ClienteDTO clienteSession = (ClienteDTO) session.getAttribute("usuarioLogueado");

        try {
            String montoStr = request.getParameter("monto");
            if (montoStr == null || montoStr.isBlank()) {
                throw new NumberFormatException();
            }

            double monto = Double.parseDouble(montoStr);

            if (service.recargarSaldo(clienteSession.getId(), monto)) {
                Cliente clienteActualizado = repositorio.getById(clienteSession.getId());

                ClienteDTO dtoActualizado = ClienteMapper.toDTO(clienteActualizado);
                dtoActualizado.setContrasenia(null);
                session.setAttribute("usuarioLogueado", dtoActualizado);
            }

            response.sendRedirect("customerProfile.jsp?success=Recarga exitosa");
            return;

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Por favor, ingresa un monto válido.");
            request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
        } catch (Exception e) {
            log("Error en recarga: ", e);
            request.setAttribute("errorMessage", "Error técnico al procesar la recarga.");
            request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
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
