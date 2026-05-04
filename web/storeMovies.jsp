<%@page import="co.edu.udistrital.model.entities.Alquiler"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="co.edu.udistrital.model.entities.Pelicula" %>
<%@ page import="co.edu.udistrital.model.repository.PeliculaRepository" %>
<!doctype html>
<html lang="es">
    <head>
        <title>Tienda de Películas - RentZone</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="css/base-layout.css" />
        <link rel="stylesheet" href="css/homePage-style.css" />
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
    </head>
    <body>
        <header class="header">
            <div class="sidebar-btn" id="sidebar-btn">
                <i class="bx bx-menu"></i>
            </div>
            <a href="HomePageServlet" class="brand">
                <img src="" alt="" class="brand-logo" />
                <span class="brand-name">RentZone</span>
            </a>
            <div class="header-title">
                <span>Catálogo de Películas</span>
            </div>
            <div class="header-actions">
                <a href="customerProfile.jsp" class="header-icon" title="Mi Perfil"><i class='bx bxs-user-circle'></i></a>
                <a href="LogoutServlet" class="header-icon" title="Cerrar Sesión"><i class='bx bx-log-out'></i></a>
            </div>
        </header>

        <main class="main">
            <div class="sidebar" id="sidebar">
                <div class="menu-container">
                    <div class="search">
                        <i class="bx bx-search"></i>
                        <input type="search" placeholder="Buscar película..." />
                    </div>
                    <ul class="menu">
                        <li class="menu-item menu-item-static">
                            <a href="HomePageServlet" class="menu-link">
                                <i class="bx bx-home-alt-2"></i>
                                <span>Home</span>
                            </a>
                        </li>
                        <li class="menu-item menu-item-static">
                            <a href="RentalServlet" class="menu-link">
                                <i class="bx bx-movie-play"></i>
                                <span>Alquileres</span>
                            </a>
                        </li>
                        <li class="menu-item menu-item-dropdown">
                            <a class="menu-link">
                                <i class="bx bx-store-alt"></i>
                                <span>Tienda</span>
                                <i class="bx bx-chevron-right arrow"></i>
                            </a>
                            <ul class="sub-menu">
                                <li class="sub-menu-item"><a href="storeGames.jsp" class="sub-menu-link">Juegos</a></li>
                                <li class="sub-menu-item"><a href="storeMovies.jsp" class="sub-menu-link">Películas</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
                <footer class="footer">
                    <ul class="menu">
                        <li class="menu-item menu-item-static">
                            <a href="customerProfile.jsp" class="menu-link">
                                <i class="bx bxs-user-circle"></i>
                                <span>Perfil</span>
                            </a>
                        </li>
                        <li class="menu-item menu-item-static">
                            <a href="development.html" class="menu-link">
                                <i class="bx bx-cog"></i>
                                <span>Configuración</span>
                            </a>
                        </li>
                    </ul>
                </footer>
            </div>

            <div class="page-content">
                <div class="product-board">
                    <section class="product-section">
                        <header class="product-section-header">
                            <h2 class="product-section-title">Películas Disponibles</h2>
                            <span class="product-section-subtitle">Los mejores estrenos y clásicos en la mejor calidad.</span>
                        </header>
                        <div class="product-list">
                            <%
                                PeliculaRepository pr = new PeliculaRepository();
                                List<Pelicula> peliculas = pr.getAll();
                                if (peliculas.isEmpty()) {
                            %>
                            <p>No hay películas disponibles en este momento.</p>
                            <% } else {
                                for (Pelicula p : peliculas) {
                            %>
                            <article class="product-card">
                                <div class="product-card-presentation">
                                    <span><%= (p.getFormato() != null ? p.getFormato() : "Digital")%> · <%= (p.getDuracion() != null ? p.getDuracion() : "N/A")%></span>
                                    <h3 class="product-card-title"><%= p.getNombreProducto()%></h3>
                                </div>
                                <div class="product-card-details">
                                    <span>Costo: $<%= p.getCostoBase()%> / día</span>
                                    <span style="color: var(--color-text-secondary); font-weight: normal;">Stock disponible: <%= p.getDisponibilidad()%></span>
                                </div>
                                <%
                                    List<Alquiler> alquileres = (List<Alquiler>) request.getAttribute("alquileres");

                                    boolean yaAlquilado = false;
                                    if (alquileres != null) {
                                        for (Alquiler alc : alquileres) {
                                            if (alc.getIdProducto().equals(p.getId())) {
                                                yaAlquilado = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (!yaAlquilado && p.getDisponibilidad() > 0) {
                                %>
                                <!-- Opción A: No está alquilado -> Mostrar Formulario normal -->
                                <form action="rentalForm.jsp" method="GET" class="form-contents">
                                    <input type="hidden" name="idProducto" value="<%= p.getId()%>">
                                    <button type="submit" class="product-card-btn">
                                        <i class='bx bx-cart-add'></i>
                                    </button>
                                </form>
                                <%
                                } else {
                                %>
                                <!-- Opción B: Ya está alquilado -> Botón gris y deshabilitado -->
                                <div class="form-contents">
                                    <button type="button" class="product-card-btn" style="background-color: #6c757d; cursor: not-allowed;" title="Ya tienes este producto alquilado">
                                        <i class='bx bx-check-double'></i>
                                    </button>
                                </div>
                                <%
                                    }
                                %>
                            </article>
                            <%  }
                            }%>
                        </div>
                    </section>
                </div>
                <footer class="page-footer">
                    <p>&copy; 2026 RentZone. Inc</p>
                </footer>
            </div>
        </main>
        <script src="js/homePage-script.js"></script>
    </body>
</html>
