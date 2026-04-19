/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package co.edu.udistrital.servlets;

import co.edu.udistrital.model.dto.AlquilerDetalleDTO;
import co.edu.udistrital.model.entities.Alquiler;
import co.edu.udistrital.model.entities.Cliente;
import co.edu.udistrital.model.entities.Juego;
import co.edu.udistrital.model.entities.Pelicula;
import co.edu.udistrital.model.entities.Producto;
import co.edu.udistrital.model.repository.AlquilerRepository;
import co.edu.udistrital.model.repository.JuegoRepository;
import co.edu.udistrital.model.repository.PeliculaRepository;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Acer-Pc
 */
@WebServlet(name = "HomePageServlet", urlPatterns = {"/HomePageServlet"})
public class HomePageServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false); // No crear sesión si no existe

        // --- VALIDACIÓN DE SESIÓN EXPIRADA ---
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp?error=Sesion expirada o no iniciada");
            return;
        }

        // Obtener repositorios del Contexto
        JuegoRepository jr = (JuegoRepository) getServletContext().getAttribute("juegoRepository");
        PeliculaRepository pr = (PeliculaRepository) getServletContext().getAttribute("peliculaRepository");
        AlquilerRepository ar = (AlquilerRepository) getServletContext().getAttribute("alquilerRepository");

        // Obtener cliente de la sesión
        Cliente cliente = (Cliente) session.getAttribute("usuarioLogueado");

        // --- LÓGICA DE NOVEDADES (Mezcla de Juegos y Pelis) ---
        List<Juego> listaJuegos = jr.getAll().reversed();
        List<Pelicula> listaPeliculas = pr.getAll().reversed();
        List<Producto> novedades = new ArrayList<>();

        int lim = Math.min(6, listaJuegos.size() + listaPeliculas.size());
        int maxIteraciones = Math.max(listaJuegos.size(), listaPeliculas.size());

        for (int i = 0; i < maxIteraciones && novedades.size() < lim; i++) {
            if (i < listaJuegos.size() && novedades.size() < lim) {
                novedades.add(listaJuegos.get(i));
            }
            if (i < listaPeliculas.size() && novedades.size() < lim) {
                novedades.add(listaPeliculas.get(i));
            }
        }

        // --- LÓGICA DE ALQUILERES VIGENTES ---
        List<Alquiler> misAlquileres = ar.getByCustomer(cliente.getId());
        List<AlquilerDetalleDTO> listaParaVista = new ArrayList<>();

        for (Alquiler a : misAlquileres) {
            String nombre = "Desconocido";
            String tipo = a.getIdProducto().startsWith("Jg") ? "Juego" : "Película";

            if (tipo.equals("Juego")) {
                Juego j = jr.getById(a.getIdProducto());
                if (j != null) {
                    nombre = j.getNombreProducto();
                }
            } else {
                Pelicula p = pr.getById(a.getIdProducto());
                if (p != null) {
                    nombre = p.getNombreProducto();
                }
            }

            listaParaVista.add(new AlquilerDetalleDTO(a, nombre, tipo));
        }

// Pasar datos limpios al JSP
        request.setAttribute("alquileresDetalle", listaParaVista);

        // Pasar datos al JSP
        request.setAttribute("novedades", novedades);
        request.setAttribute("alquileres", misAlquileres);

        // Reenviar a la vista (JSP)
        request.getRequestDispatcher("customerHomePage.jsp").forward(request, response);
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
