<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List"%>
<%@ page import="co.edu.udistrital.model.dto.*" %>
<%@ page import="co.edu.udistrital.model.entities.*" %>
<!doctype html>
<html lang="es">

    <head>
        <title>Home</title>
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
                <span>Página Principal</span>
            </div>
            <div class="header-actions">
                <a href="customerProfile.jsp" class="header-icon" title="Perfil"><i class='bx bxs-user-circle'></i></a>
                <a href="LogoutServlet" class="header-icon" title="Cerrar Sesión"><i class='bx bx-log-out'></i></a>
            </div>
        </header>
        <main class="main">
            <div class="sidebar" id="sidebar">
                <div class="menu-container">
                    <div class="search">
                        <i class="bx bx-search"></i>
                        <input type="search" placeholder="Buscar..." />
                    </div>
                    <ul class="menu">
                        <li class="menu-item active menu-item-static">
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
                <div class="product-board">
                    <section class="product-section">
                        <header class="product-section-header">
                            <h2 class="product-section-title">Novedades Destacadas</h2>
                            <span class="product-section-subtitle">Últimos agregados en nuestra tienda</span>
                        </header>
                        <div class="product-list">
                            <%
                                List<Producto> productoList = (List<Producto>) request.getAttribute("novedades");
                                if (productoList == null || productoList.isEmpty()) {
                            %>
                            <p style="color: var(--color-text-secondary); padding: 1rem;">No hay productos disponibles.</p>
                            <%
                            } else {
                                for (Producto p : productoList) {
                                    String categoria = (p instanceof Juego) ? "Juego" : "Película";
                                    String formato = "";
                                    String detalleExtra = "";
                                    if (p instanceof Juego) {
                                        formato = ((Juego) p).getPlataforma();
                                        detalleExtra = ((Juego) p).getGenero();
                                    } else if (p instanceof Pelicula) {
                                        formato = ((Pelicula) p).getFormato();
                                        detalleExtra = ((Pelicula) p).getDuracion();
                                    }
                            %>
                            <article class="product-card">
                                <div class="product-card-presentation">
                                    <span><%= (formato != null) ? formato : ""%> · <%= categoria%></span>
                                    <h3 class="product-card-title"><%= p.getNombreProducto()%></h3>
                                </div>
                                <div class="product-card-details">
                                    <span><%= detalleExtra%></span>
                                    <span>Precio: $ <%= p.getCostoBase()%> / dia</span>
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

                                    if (!yaAlquilado) {
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
                            <%
                                    } // Cierre For
                                } // Cierre Else
                            %>
                        </div>
                    </section>

                    <!-- SECCIÓN ALQUILERES -->
                    <section class="product-section">
                        <header class="product-section-header">
                            <h2 class="product-section-title">Alquileres Vigentes</h2>
                        </header>
                        <div class="product-list">
                            <%
                                List<AlquilerDetalleDTO> alquileres = (List<AlquilerDetalleDTO>) request.getAttribute("alquileres");
                                if (alquileres == null || alquileres.isEmpty()) {
                            %>
                            <p style="color: var(--color-text-secondary); padding: 1rem;">No tienes alquileres vigentes.</p>
                            <%
                            } else {
                                int count = 0;
                                for (AlquilerDetalleDTO a : alquileres) {
                                    if (count == 6) {
                                        break;
                                    }
                            %>
                            <article class="product-card">
                                <div class="product-card-presentation">
                                    <span><%= a.getTipoProducto()%></span>
                                    <h3 class="product-card-title"><%= a.getNombreProducto()%></h3>
                                </div>
                                <div class="product-card-details">
                                    <span>Retorno: <%= a.getAlquiler().getFechaPactada()%></span>
                                </div>
                            </article>
                            <%
                                        count++;
                                    } // Cierre For Alquileres
                                } // Cierre Else Alquileres
                            %>
                        </div>
                    </section>
                </div>  
                <footer class="page-footer">
                    <p>&copy; 2026 RentZone. Todos los derechos reservados.</p>
                </footer>
            </div>
        </main>

        <script src="js/homePage-script.js"></script>
        <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    </body>

</html>