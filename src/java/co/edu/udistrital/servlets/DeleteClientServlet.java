package co.edu.udistrital.servlets;

import co.edu.udistrital.model.repository.ClienteRepository;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteClientServlet", urlPatterns = {"/DeleteClientServlet"})
public class DeleteClientServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            ClienteRepository repository = (ClienteRepository) getServletContext().getAttribute("clienteRepository");
            if (repository == null) {
                repository = new ClienteRepository();
                getServletContext().setAttribute("clienteRepository", repository);
            }

            String idCliente = request.getParameter("id");
            if (idCliente != null && !idCliente.trim().isEmpty()) {
                repository.eliminar(idCliente);
            }

            response.sendRedirect("clients.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Delete Client Controller";
    }
}
