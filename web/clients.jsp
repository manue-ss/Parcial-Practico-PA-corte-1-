<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="co.edu.udistrital.model.entities.Cliente" %>
<%@ page import="co.edu.udistrital.model.repository.ClienteRepository" %>
<!doctype html>
<html lang="es">
    <head>
        <title>Directorio de Clientes - RentZone Staff</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="css/base-layout.css" />
        <link rel="stylesheet" href="css/employeeHomePage-style.css" />
        <link rel="stylesheet" href="css/homePage-style.css" /> <!-- Para badge styles si aplican -->
        <link rel="stylesheet" href="css/forms.css" />
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
                <span>Gestión de Clientes</span>
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
                        <input type="search" placeholder="Buscar cliente..." />
                    </div>
                    <ul class="menu">
                        <li class="menu-item menu-item-static">
                            <a href="employeeHomePage.jsp" class="menu-link">
                                <i class='bx bx-grid-alt'></i>
                                <span>Dashboard</span>
                            </a>
                        </li>
                        <li class="menu-item menu-item-static">
                            <a href="stock.jsp" class="menu-link">
                                <i class='bx bx-archive'></i>
                                <span>Stock</span>
                            </a>
                        </li>
                        <li class="menu-item active menu-item-static">
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
                        <header class="dashboard-widget-header">
                            <h2 class="dashboard-widget-title">Directorio Público</h2>
                            <div class="dashboard-widget-subtitle">Seleccione un cliente para auditar su perfil o membresía.</div>
                        </header>

                        <div class="clients-grid">

                            <%
                                ClienteRepository cr = new ClienteRepository();
                                List<Cliente> clientes = cr.getAll();
                                if (clientes.isEmpty()) {
                            %>
                            <p>No se encontraron clientes registrados en la base de datos.</p>
                            <%  } else {
                                for (Cliente c : clientes) {
                                    String mem = c.getMembresia().name();
                                    String badgeClass = mem.equals("NORMAL") ? ""
                                            : (mem.equals("VIP") ? "vip" : "premium");
                            %>
                            <a href="clientProfileEdit.jsp?id=<%= c.getId()%>" class="client-card">
                                <div class="client-avatar"><i class='bx bx-user'></i></div>
                                <div class="client-username"><%= c.getNombreUsuario()%></div>
                                <div class="client-name"><%= c.getNombreCompleto()%></div>
                                <span class="badge <%= badgeClass%>"><%= mem%></span>
                            </a>
                            <%      }
                                }
                            %>

                        </div>
                    </section>
                </div>
            </div>
        </main>

        <script src="js/homePage-script.js"></script>
    </body>
</html>
