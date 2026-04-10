package co.edu.udistrital.servlets;

import co.edu.udistrital.model.entities.Empleado;
import co.edu.udistrital.model.repository.EmpleadoRepository;
import co.edu.udistrital.model.service.IniciarSesionEmpleado;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet encargado de aislar completamente el acceso del Personal del Staff
 * hacia el Dashboard administrativo.
 *
 *
 * @author Manuel Salazar
 * @since 0.1
 */
@WebServlet(name = "EmployeeLoginServlet", urlPatterns = {"/EmployeeLoginServlet"})
public class EmployeeLoginServlet extends HttpServlet {

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
            // Obtención del repositorio desde el contexto de la aplicación
            EmpleadoRepository empRepo = (EmpleadoRepository) getServletContext().getAttribute("empleadoRepository");

            // En caso de que no esté en el contexto, se inicializa (Seguridad)
            if (empRepo == null) {
                empRepo = new EmpleadoRepository();
            }

            IniciarSesionEmpleado authService = new IniciarSesionEmpleado(empRepo);

            String username = request.getParameter("username");
            String pass = request.getParameter("password");

            Empleado emp = authService.autenticar(username, pass);

            if (emp != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("empleadoLogueado", emp);
                response.sendRedirect("employeeHomePage.jsp");
            } else {
                request.setAttribute("error", "Credenciales inválidas para el portal de empleados.");
                request.getRequestDispatcher("employeeLogin.jsp").forward(request, response);
            }
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
        return "Employee Authentication Controller";
    }// </editor-fold>
}
