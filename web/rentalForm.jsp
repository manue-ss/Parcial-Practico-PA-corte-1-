<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="co.edu.udistrital.model.entities.*" %>
<%@ page import="co.edu.udistrital.model.repository.*" %>
<!doctype html>
<html lang="es">
<head>
    <title>Confirmar Alquiler - RentZone</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/base-layout.css" />
    <link rel="stylesheet" href="css/homePage-style.css" />
    <link rel="stylesheet" href="css/forms.css" />
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
            <span>Confirmación de Alquiler</span>
        </div>
        <div class="header-actions">
            <a href="customerProfile.jsp" class="header-icon" title="Mi Perfil"><i class='bx bxs-user-circle'></i></a>
            <a href="LogoutServlet" class="header-icon" title="Cerrar Sesión"><i class='bx bx-log-out'></i></a>
        </div>
    </header>
    
    <main class="main">
        <div class="sidebar" id="sidebar">
            <div class="menu-container">
                <ul class="menu">
                    <li class="menu-item menu-item-static">
                        <a href="HomePageServlet" class="menu-link">
                            <i class="bx bx-home-alt-2"></i>
                            <span>Home</span>
                        </a>
                    </li>
                    <li class="menu-item menu-item-static">
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
            <%
                String idProducto = request.getParameter("idProducto");
                String nombreProd = "Producto Desconocido";
                double costoBase = 0.0;
                if (idProducto != null) {
                    if (idProducto.startsWith("Jg")) {
                        Juego j = new JuegoRepository().getById(idProducto);
                        if (j != null) { nombreProd = j.getNombreProducto(); costoBase = j.getCostoBase(); }
                    } else if (idProducto.startsWith("Pl")) {
                        Pelicula p = new PeliculaRepository().getById(idProducto);
                        if (p != null) { nombreProd = p.getNombreProducto(); costoBase = p.getCostoBase(); }
                    }
                }
            %>
            <div class="action-box">
                <h2 class="action-box-title">Detalles del Alquiler</h2>
                <div class="static-info-container">
                    <h3 class="static-info-title">Producto Seleccionado: <%= nombreProd %></h3>
                    <p><strong>Costo Base:</strong> $<%= String.format("%.2f", costoBase) %> / día</p>
                </div>

                <form action="ProcessRentalServlet" method="POST">
                    <input type="hidden" name="idProducto" value="<%= idProducto != null ? idProducto : "" %>">
                    
                    <div class="form-group">
                        <label for="fechaAlquiler">Fecha de Inicio de Alquiler</label>
                        <input type="date" id="fechaAlquiler" name="fechaAlquiler" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="fechaDevolucion">Fecha Estimada de Devolución</label>
                        <input type="date" id="fechaDevolucion" name="fechaDevolucion" required>
                    </div>

                    <div class="total-estimate-container">
                        <span class="total-estimate-price">Costo Base: <span>Mínimo $<%= String.format("%.2f", costoBase) %></span></span>
                        <p class="total-estimate-note">(El costo final incluirá recargos de cálculo y descuentos de membresía a confirmar en la facturación)</p>
                    </div>

                    <div class="btn-row">
                        <button type="button" class="btn-danger" onclick="history.back()">Cancelar</button>
                        <button type="submit" class="btn-primary">Confirmar y Pagar</button>
                    </div>
                </form>
            </div>
        </div>
    </main>
    <script src="js/homePage-script.js"></script>
</body>
</html>
