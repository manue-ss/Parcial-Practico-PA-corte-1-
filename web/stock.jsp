<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="co.edu.udistrital.model.entities.*" %>
<%@ page import="co.edu.udistrital.model.repository.*" %>
<!doctype html>
<html lang="es">
<head>
    <title>Inventario Stock - RentZone Staff</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/base-layout.css" />
    <link rel="stylesheet" href="css/employeeHomePage-style.css" />
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
</head>
<body>
    <header class="header">
        <div class="sidebar-btn" id="sidebar-btn">
            <i class="bx bx-menu"></i>
        </div>
        <a href="employeeHomePage.jsp" class="brand">
            <i class='bx bx-building-house brand-icon-lg'></i>
            <span class="brand-name">RentZone - Staff</span>
        </a>
        <div class="header-title">
            <span>Gestión de Inventario</span>
        </div>
        <div class="header-actions">
            <a href="employeeProfile.jsp" class="header-icon" title="Perfil Empleado"><i class='bx bxs-user-badge'></i></a>
            <a href="EmployeeLogoutServlet" class="header-icon" title="Cerrar Sesión"><i class='bx bx-log-out'></i></a>
        </div>
    </header>
    
    <main class="main">
        <div class="sidebar" id="sidebar">
            <div class="menu-container">
                <div class="search">
                    <i class="bx bx-search"></i>
                    <input type="search" placeholder="Módulo a buscar..." />
                </div>
                <ul class="menu">
                    <li class="menu-item menu-item-static">
                        <a href="employeeHomePage.jsp" class="menu-link">
                            <i class='bx bx-grid-alt'></i>
                            <span>Dashboard</span>
                        </a>
                    </li>
                    <li class="menu-item active menu-item-static">
                        <a href="stock.jsp" class="menu-link">
                            <i class='bx bx-archive'></i>
                            <span>Stock</span>
                        </a>
                    </li>
                    <li class="menu-item menu-item-static">
                        <a href="clients.jsp" class="menu-link">
                            <i class='bx bx-group'></i>
                            <span>Clientes</span>
                        </a>
                    </li>
                    <!-- Opciones de administrador -->
                    <li class="menu-item menu-item-static">
                        <a href="employeeManagement.jsp" class="menu-link menu-link-accent">
                            <i class='bx bx-shield-quarter'></i>
                            <span>Empleados (Admin)</span>
                        </a>
                    </li>
                </ul>
            </div>
            <footer class="footer">
                <ul class="menu">
                    <li class="menu-item menu-item-static">
                        <a href="employeeProfile.jsp" class="menu-link">
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

        <div class="dashboard-content">
            <div class="dashboard-board">
                <section class="dashboard-widget">
                    <header class="dashboard-widget-header" style="display: flex; justify-content: space-between; align-items: center;">
                        <div>
                            <h2 class="dashboard-widget-title">Inventario de Productos</h2>
                            <div class="dashboard-widget-subtitle">Ajuste las unidades disponibles o introduzca nuevos artículos al catálogo.</div>
                        </div>
                        <a href="registerProduct.jsp" class="btn-small" style="font-size: 1rem; padding: 0.6rem 1.2rem;">
                            <i class='bx bx-plus' style="vertical-align: middle;"></i> Nuevo Producto
                        </a>
                    </header>
                    
                    <div class="data-table-container">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Tipo</th>
                                    <th>Especificación</th>
                                    <th>Costo Base</th>
                                    <th>Stock</th>
                                    <th>Alquilados</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    JuegoRepository jr = new JuegoRepository();
                                    PeliculaRepository pr = new PeliculaRepository();
                                    List<Juego> juegos = jr.obtenerTodos();
                                    List<Pelicula> peliculas = pr.obtenerTodos();

                                    for (Juego j : juegos) {
                                %>
                                <tr>
                                    <td><%= j.getId() %></td>
                                    <td><%= j.getNombreProducto()%></td>
                                    <td>Juego</td>
                                    <td><%= j.getPlataforma() != null ? j.getPlataforma() : "-" %></td>
                                    <td>$<%= j.getCostoBase() %></td>
                                    <td><%= j.getStock() %></td>
                                    <td><%= j.getAlquilados() %></td>
                                    <td style="display: flex; gap: 0.5rem;">
                                        <form action="UpdateStockServlet" method="POST" style="display: inline;">
                                            <input type="hidden" name="idProducto" value="<%= j.getId() %>">
                                            <input type="hidden" name="action" value="add">
                                            <button type="submit" class="btn-small" title="Añadir unidad">+</button>
                                        </form>
                                        <form action="UpdateStockServlet" method="POST" style="display: inline;">
                                            <input type="hidden" name="idProducto" value="<%= j.getId() %>">
                                            <input type="hidden" name="action" value="reduce">
                                            <button type="submit" class="btn-small danger" title="Reducir unidad">-</button>
                                        </form>
                                    </td>
                                </tr>
                                <% } %>

                                <% for (Pelicula p : peliculas) { %>
                                <tr>
                                    <td><%= p.getId() %></td>
                                    <td><%= p.getNombreProducto()%></td>
                                    <td>Película</td>
                                    <td><%= p.getFormato() != null ? p.getFormato() : "-" %></td>
                                    <td>$<%= p.getCostoBase() %></td>
                                    <td><%= p.getStock() %></td>
                                    <td><%= p.getAlquilados() %></td>
                                    <td style="display: flex; gap: 0.5rem;">
                                        <form action="UpdateStockServlet" method="POST" style="display: inline;">
                                            <input type="hidden" name="idProducto" value="<%= p.getId() %>">
                                            <input type="hidden" name="action" value="add">
                                            <button type="submit" class="btn-small" title="Añadir unidad">+</button>
                                        </form>
                                        <form action="UpdateStockServlet" method="POST" style="display: inline;">
                                            <input type="hidden" name="idProducto" value="<%= p.getId() %>">
                                            <input type="hidden" name="action" value="reduce">
                                            <button type="submit" class="btn-small danger" title="Reducir unidad">-</button>
                                        </form>
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </section>
            </div>
        </div>
    </main>

    <script src="js/homePage-script.js"></script>
</body>
</html>
