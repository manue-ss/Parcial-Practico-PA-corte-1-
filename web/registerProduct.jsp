<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="es">
    <head>
        <title>Nuevo Producto - RentZone Staff</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="css/base-layout.css" />
        <link rel="stylesheet" href="css/employeeHomePage-style.css" />
        <link rel="stylesheet" href="css/homePage-style.css" />
        <!-- Reusando forms básicos -->
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
                <span>Alta de Productos</span>
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
                    <ul class="menu">
                        <li class="menu-item menu-item-static">
                            <a href="employeeHomePage.jsp" class="menu-link">
                                <i class="bx bx-grid-alt"></i>
                                <span>Dashboard</span>
                            </a>
                        </li>
                        <li class="menu-item active menu-item-static">
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
                                <span>Configuracion</span>
                            </a>
                        </li>
                    </ul>
                </footer>
            </div>

            <div class="dashboard-content">
                <div class="action-box">
                    <h2 class="action-box-title">
                        Registrar Artículo al Catálogo
                    </h2>

                    <form action="RegisterProductServlet" method="POST">
                        <div class="form-group">
                            <label for="tipoProducto">Clase de Producto</label>
                            <select
                                id="tipoProducto"
                                name="tipoProducto"
                                required>
                                <option value="" disabled selected>
                                    Seleccione el tipo...
                                </option>
                                <option value="JUEGO">Videojuego</option>
                                <option value="PELICULA">Película</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="nombreProducto">Título / Nombre</label>
                            <input
                                type="text"
                                id="nombreProducto"
                                name="nombreProducto"
                                required />
                        </div>

                        <div style="display: flex; gap: 1rem">
                            <div class="form-group" style="flex: 1">
                                <label for="costoBase">Costo Base ($)</label>
                                <input
                                    type="number"
                                    id="costoBase"
                                    name="costoBase"
                                    step="0.01"
                                    min="0"
                                    required />
                            </div>
                            <div class="form-group" style="flex: 1">
                                <label for="stock">Stock Físico Inicial</label>
                                <input
                                    type="number"
                                    id="stock"
                                    name="stock"
                                    min="1"
                                    required />
                            </div>
                        </div>

                        <!-- Campos compartidos dinámicos verbalmente, controlados por Java al procesar. -->
                        <div
                            style="
                                margin-top: 1.5rem;
                                padding-top: 1rem;
                                border-top: 1px solid var(--color-border);
                            ">
                            <p
                                style="
                                    font-size: 0.85rem;
                                    color: var(--color-text-secondary);
                                    margin-bottom: 1rem;
                                ">
                                Especifique los datos según el tipo seleccionado
                                arriba (ignore los que no apliquen):
                            </p>

                            <div style="display: flex; gap: 1rem">
                                <div class="form-group" style="flex: 1">
                                    <label for="especificacion"
                                        >Formato / Plataforma</label
                                    >
                                    <input
                                        type="text"
                                        id="especificacion"
                                        name="especificacion"
                                        placeholder="Ej: Blu-Ray o PS5" />
                                </div>
                                <div class="form-group" style="flex: 1">
                                    <label for="detalleSecundario"
                                        >Duración / Género</label
                                    >
                                    <input
                                        type="text"
                                        id="detalleSecundario"
                                        name="detalleSecundario"
                                        placeholder="Ej: 120 min o RPG" />
                                </div>
                            </div>
                        </div>

                        <div
                            style="
                                display: flex;
                                gap: 1rem;
                                margin-top: 1.5rem;
                            ">
                            <a
                                href="stock.jsp"
                                class="btn-danger"
                                style="
                                    text-align: center;
                                    text-decoration: none;
                                    padding-top: 0.85rem;
                                "
                                >Regresar</a
                            >
                            <button
                                type="submit"
                                class="btn-primary"
                                style="margin-top: 0">
                                Guardar Producto
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
        <script src="js/homePage-script.js"></script>
    </body>
</html>
