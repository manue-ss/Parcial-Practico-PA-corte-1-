<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="co.edu.udistrital.model.dto.*" %>
<%@ page import="co.edu.udistrital.model.repository.*" %>
<!doctype html>
<html lang="es">
    <head>
        <title>Mis Alquileres - RentZone</title>
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
                <span>Mis Alquileres</span>
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
                        <input type="search" placeholder="Quick Search..." />
                    </div>
                    <ul class="menu">
                        <li class="menu-item menu-item-static">
                            <a href="HomePageServlet" class="menu-link">
                                <i class="bx bx-home-alt-2"></i>
                                <span>Home</span>
                            </a>
                        </li>
                        <li class="menu-item active menu-item-static">
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
                            <h2 class="product-section-title">En Posesión</h2>
                            <span class="product-section-subtitle">Tus productos alquilados actualmente. Recuerda devolverlos a tiempo.</span>
                        </header>
                        <div class="product-list">
                            <%
                                List<AlquilerDTO> vigentes = (List<AlquilerDTO>) request.getAttribute("alquileresVigentes");
                                if (vigentes == null || vigentes.isEmpty()) {
                            %>
                            <p>No tienes productos alquilados actualmente.</p>
                            <% } else {
                                for (AlquilerDTO a : vigentes) {%>
                            <article class="product-card">
                                <div class="product-card-presentation">
                                    <span><%= a.getTipoProducto()%></span>
                                    <h3 class="product-card-title"><%= a.getNombreProducto()%></h3>
                                </div>
                                <div class="product-card-details">
                                    <span>Inicio: <%= a.getFechaAlquiler()%></span>
                                    <span>Retorno: <%= a.getFechaPactada()%></span>
                                    <span>Total: $<%= String.format("%.2f", a.getCostoTotal())%></span>
                                </div>
                                <form action="ReturnRentalServlet" method="POST">
                                    <input type="hidden" name="idAlquiler" value="<%= a.getIdAlquiler()%>">
                                    <button type="submit" class="product-card-btn danger">
                                        <i class='bx bx-x'></i>
                                    </button>
                                </form>
                            </article>
                            <% }
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
