<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="es">
    <head>
        <title>Portal Empleados - Dashboard</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="css/base-layout.css" />
        <link rel="stylesheet" href="css/employeeHomePage-style.css" />
        <link
            href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css"
            rel="stylesheet" />
    </head>
    <body>
        <header class="header">
            <div class="sidebar-btn" id="sidebar-btn">
                <i class="bx bx-menu"></i>
            </div>
            <a href="employeeHomePage.jsp" class="brand">
                <i class="bx bx-building-house brand-icon-lg"></i>
                <span class="brand-name">RentZone - Staff</span>
            </a>
            <div class="header-title">
                <span>Control Panel</span>
            </div>
            <div class="header-actions">
                <a
                    href="employeeProfile.jsp"
                    class="header-icon"
                    title="Perfil Empleado"
                    ><i class="bx bxs-user-badge"></i
                ></a>
                <a
                    href="EmployeeLogoutServlet"
                    class="header-icon"
                    title="Cerrar Sesión"
                    ><i class="bx bx-log-out"></i
                ></a>
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
                        <li class="menu-item active menu-item-static">
                            <a href="employeeHomePage.jsp" class="menu-link">
                                <i class="bx bx-grid-alt"></i>
                                <span>Dashboard</span>
                            </a>
                        </li>
                        <li class="menu-item menu-item-static">
                            <a href="stock.jsp" class="menu-link">
                                <i class="bx bx-archive"></i>
                                <span>Stock</span>
                            </a>
                        </li>
                        <li class="menu-item menu-item-static">
                            <a href="clients.jsp" class="menu-link">
                                <i class="bx bx-group"></i>
                                <span>Clientes</span>
                            </a>
                        </li>
                        <!-- Opciones de administrador -->
                        <li class="menu-item menu-item-static">
                            <a
                                href="employeeManagement.jsp"
                                class="menu-link menu-link-accent">
                                <i class="bx bx-shield-quarter"></i>
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
                            <h2 class="dashboard-widget-title">
                                Resumen General
                            </h2>
                        </header>
                        <div class="dashboard-widget-subtitle">
                            <p>
                                Bienvenido al módulo interno de administración.
                            </p>
                            <p>
                                Contenido del dashboard se cargará próximamente.
                            </p>
                        </div>
                    </section>
                </div>
                <footer class="page-footer">
                    <p>&copy; 2026 RentZone Staff. Uso interno exclusivo.</p>
                </footer>
            </div>
        </main>

        <script src="js/homePage-script.js"></script>
    </body>
</html>
