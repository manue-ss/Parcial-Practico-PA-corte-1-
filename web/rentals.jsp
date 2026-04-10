<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="co.edu.udistrital.model.entities.*" %>
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
                        <a href="rentals.jsp" class="menu-link">
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
                            Cliente c = (Cliente) session.getAttribute("usuarioLogueado");
                            if (c != null) {
                                AlquilerRepository ar = new AlquilerRepository();
                                JuegoRepository jr = new JuegoRepository();
                                PeliculaRepository pr = new PeliculaRepository();
                                List<Alquiler> misAlquileres = ar.obtenerPorUsuario(c.getNombreUsuario());
                                
                                boolean tieneVigentes = false;
                                for (Alquiler a : misAlquileres) { if(a.isVigente()) tieneVigentes = true; }

                                if(!tieneVigentes) { 
                        %>
                                    <p>No tienes productos alquilados actualmente.</p>
                        <%      } else {
                                    for (Alquiler a : misAlquileres) { 
                                        if(!a.isVigente()) continue;
                                        String titulo = "Producto Desconocido";
                                        String detalles = "";
                                        if (a.getIdProducto().startsWith("Jg")) {
                                            Juego j = jr.obtenerPorId(a.getIdProducto());
                                            if(j != null) { titulo = j.getNombreProducto(); detalles = j.getPlataforma() + " · " + j.getGenero(); }
                                        } else {
                                            Pelicula p = pr.obtenerPorId(a.getIdProducto());
                                            if(p != null) { titulo = p.getNombreProducto(); detalles = p.getFormato() + " · " + p.getDuracion(); }
                                        }
                        %>
                        <article class="product-card">
                            <div class="product-card-presentation">
                                <span><%= detalles %></span>
                                <h3 class="product-card-title"><%= titulo %></h3>
                            </div>
                            <div class="product-card-details">
                                <span>Inicio: <%= a.getFechaSalida() %></span>
                                <span>Retorno: <%= a.getFechaEntregaPactada() %></span>
                                <span>Total cancelado: $<%= String.format("%.2f", a.getCostoTotal()) %></span>
                            </div>
                            <form action="ReturnRentalServlet" method="POST" class="form-contents">
                                <input type="hidden" name="idAlquiler" value="<%= a.getIdAlquiler() %>">
                                <button type="submit" class="product-card-btn danger" title="Devolver Producto">
                                    <i class='bx bx-x'></i>
                                </button>
                            </form>
                        </article>
                        <%          } 
                                }
                            }
                        %>
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
