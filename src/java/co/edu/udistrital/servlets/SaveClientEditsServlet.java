package co.edu.udistrital.servlets;

import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.repository.ClienteRepository;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SaveClientEditsServlet", urlPatterns = {"/SaveClientEditsServlet"})
public class SaveClientEditsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configuramos la codificación para evitar problemas con tildes o la 'ñ'
        request.setCharacterEncoding("UTF-8");

        // 1. Obtener el repositorio del Contexto (el que cargó el Listener)
        ClienteRepository repository = (ClienteRepository) getServletContext().getAttribute("clienteRepository");

        if (repository == null) {
            repository = new ClienteRepository();
            getServletContext().setAttribute("clienteRepository", repository);
        }

        // 2. CAPTURA DE DATOS - Usamos 'nombreUsuario' porque es la clave del Map
        String usernameKey = request.getParameter("nombreUsuario");
        String nombreCompleto = request.getParameter("nombreCompleto");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");

        double saldo = 0;
        try {
            String saldoStr = request.getParameter("saldo");
            if (saldoStr != null) {
                saldo = Double.parseDouble(saldoStr);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir saldo: " + e.getMessage());
        }

        // 3. LÓGICA DE ACTUALIZACIÓN
        if (usernameKey != null && !usernameKey.trim().isEmpty()) {

            // Buscamos el objeto usando el USERNAME como identificador
            Cliente clienteToUpdate = repository.getByUsername(usernameKey);

            if (clienteToUpdate != null) {
                // Actualizamos los atributos del objeto que está en el Map
                clienteToUpdate.setNombreCompleto(nombreCompleto);
                clienteToUpdate.setCorreo(correo);
                clienteToUpdate.setTelefono(telefono);
                clienteToUpdate.setSaldo(saldo);

                // IMPORTANTE: Este método debe persistir los cambios en el JSON
                repository.update(clienteToUpdate);

                System.out.println("LOG: Cliente " + usernameKey + " actualizado con éxito.");
            } else {
                System.out.println("LOG ERROR: No se encontró el cliente con username: " + usernameKey);
            }
        }

        // 4. REDIRECCIÓN - Siempre volvemos a la lista después de procesar
        response.sendRedirect("clients.jsp");
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
        return "Servlet que procesa la edición de datos de un cliente usando su username como clave";
    }
}
