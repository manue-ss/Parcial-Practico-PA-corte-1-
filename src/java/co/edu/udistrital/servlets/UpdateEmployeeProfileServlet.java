package co.edu.udistrital.servlets;

import co.edu.udistrital.model.entities.Empleado;
import co.edu.udistrital.model.repository.EmpleadoRepository;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "UpdateEmployeeProfileServlet", urlPatterns = {"/UpdateEmployeeProfileServlet"})
public class UpdateEmployeeProfileServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("empleadoActual") == null) {
                response.sendRedirect("employeeLogin.jsp");
                return;
            }

            EmpleadoRepository repository = (EmpleadoRepository) getServletContext().getAttribute("empleadoRepository");
            if (repository == null) {
                repository = new EmpleadoRepository();
                getServletContext().setAttribute("empleadoRepository", repository);
            }

            Empleado empleadoActual = (Empleado) session.getAttribute("empleadoActual");
            String nombre = request.getParameter("nombreCompleto");
            String correo = request.getParameter("correo");
            String telefono = request.getParameter("telefono");
            String direccion = request.getParameter("direccion");

            empleadoActual.setNombreCompleto(nombre);
            empleadoActual.setCorreo(correo);
            empleadoActual.setTelefono(telefono);
            empleadoActual.setDireccion(direccion);

            repository.update(empleadoActual);
            session.setAttribute("empleadoActual", empleadoActual);

            response.sendRedirect("employeeProfile.jsp");
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
        return "Employee Profile Update Controller";
    }
}
