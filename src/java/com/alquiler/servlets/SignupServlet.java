/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.alquiler.servlets;

import com.alquiler.model.dto.ClienteDTO;
import com.alquiler.model.repository.ClienteRepository;
import com.alquiler.model.service.RegistrarUsuario;
import com.alquiler.util.SignupValidator;
import com.alquiler.util.ValidationResult;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Acer-Pc
 */
@WebServlet(name = "SignupServlet", urlPatterns = {"/SignupServlet"})
public class SignupServlet extends HttpServlet {

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
            ClienteDTO cliente = new ClienteDTO();
            ClienteRepository repositorio = (ClienteRepository) getServletContext().getAttribute("clienteRepository");

            cliente.setNombreCompleto(request.getParameter("name"));
            cliente.setNombreUsuario(request.getParameter("username"));
            cliente.setCorreo(request.getParameter("email"));
            cliente.setTelefono(request.getParameter("phone"));
            cliente.setContrasenia(request.getParameter("password"));

            ValidationResult resultado = SignupValidator.validar(cliente, repositorio);
            if (!resultado.isValido()) {
                request.setAttribute("errorSignupMessage", resultado.getMensaje());
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            } else {
                // Validación pasó, proceder con registro
                RegistrarUsuario registrar = new RegistrarUsuario(repositorio);
                registrar.ejecutar(cliente);
                // Éxito: redirigir o mostrar mensaje
                request.setAttribute("successMessage", "Usuario registrado exitosamente. Inicia sesión.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            request.getRequestDispatcher("index.jsp").forward(request, response);

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
        return "Short description";
    }// </editor-fold>

}
