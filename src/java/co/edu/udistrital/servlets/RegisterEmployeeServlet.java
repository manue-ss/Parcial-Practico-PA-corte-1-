package co.edu.udistrital.servlets;

import co.edu.udistrital.model.entities.Empleado;
import co.edu.udistrital.model.repository.EmpleadoRepository;
import co.edu.udistrital.util.SecurityUtil;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterEmployeeServlet", urlPatterns = {"/RegisterEmployeeServlet"})
public class RegisterEmployeeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            EmpleadoRepository repository = (EmpleadoRepository) getServletContext().getAttribute("empleadoRepository");
            if (repository == null) {
                repository = new EmpleadoRepository();
                getServletContext().setAttribute("empleadoRepository", repository);
            }

            String dni = request.getParameter("dni");
            String cargo = request.getParameter("cargo");
            String nombreUsuario = request.getParameter("nombreUsuario");
            String contrasenia = request.getParameter("contrasenia");
            String nombreCompleto = request.getParameter("nombreCompleto");
            String correo = request.getParameter("correo");
            String telefono = request.getParameter("telefono");
            String direccion = request.getParameter("direccion");

            // Basic ID generation for new employees
            String generatedId = "EM-" + System.currentTimeMillis() % 10000;

            Empleado nuevoEmpleado = new Empleado(
                    dni,
                    direccion,
                    cargo,
                    generatedId,
                    nombreUsuario,
                    SecurityUtil.encriptarSHA256(contrasenia),
                    nombreCompleto,
                    correo,
                    telefono
            );

            repository.add(nuevoEmpleado);

            response.sendRedirect("employeeManagement.jsp?success=1");
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
        return "Employee Registration Controller";
    }
}
