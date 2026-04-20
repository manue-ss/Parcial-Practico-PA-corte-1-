<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="co.edu.udistrital.model.entities.Cliente" %>
<%@ page import="co.edu.udistrital.model.repository.ClienteRepository" %>
<%
    String idClienteForm = request.getParameter("id");
    ClienteRepository cr = (ClienteRepository) getServletContext().getAttribute("clienteRepository");
    if(cr == null) { cr = new ClienteRepository(); }
    Cliente clienteToEdit = cr.getById(idClienteForm);

    if (clienteToEdit == null) {
        response.sendRedirect("clients.jsp");
        return;
    }
    String mem = clienteToEdit.getMembresia().name();
    String badgeClass = mem.equals("NORMAL") ? "" : (mem.equals("VIP") ? "vip" : "premium");
%>
<!doctype html>
<html lang="es">
    <head>
        <title>Edición de Cliente - RentZone Staff</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="css/base-layout.css" />
        <link rel="stylesheet" href="css/employeeHomePage-style.css" />
        <link rel="stylesheet" href="css/homePage-style.css" />
        <!-- Para action-box y form-group -->
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
                <span>Auditoría de Cuenta Cliente</span>
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
                        <li class="menu-item menu-item-static">
                            <a href="stock.jsp" class="menu-link">
                                <i class="bx bx-archive"></i>
                                <span>Stock</span>
                            </a>
                        </li>
                        <li class="menu-item active menu-item-static">
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
                    <div
                        style="
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            border-bottom: 2px solid var(--color-border);
                            padding-bottom: 0.5rem;
                            margin-bottom: 1.5rem;
                        ">
                        <h2
                            class="action-box-title"
                            style="border: none; margin: 0; padding: 0">
                            Editar Cliente: <%= clienteToEdit.getNombreUsuario() %>
                        </h2>
                        <span class="badge <%= badgeClass %>"><%= mem %></span>
                    </div>

                    <form action="SaveClientEditsServlet" method="POST">
                        <input type="hidden" name="idCliente" value="<%= clienteToEdit.getId() %>" />

                        <div style="display: flex; gap: 1rem">
                            <div class="form-group" style="flex: 1">
                                <label for="nombreUsuario"
                                    >Usuario (ID Login)</label
                                >
                                <input
                                    type="text"
                                    id="nombreUsuario"
                                    name="nombreUsuario"
                                    value="<%= clienteToEdit.getNombreUsuario() %>"
                                    readonly
                                    style="
                                        background-color: var(--color-bg);
                                        opacity: 0.7;
                                    " />
                            </div>
                            <div class="form-group" style="flex: 1">
                                <label for="nombreCompleto">Nombre Real</label>
                                <input
                                    type="text"
                                    id="nombreCompleto"
                                    name="nombreCompleto"
                                    value="<%= clienteToEdit.getNombreCompleto() %>"
                                    required />
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="correo"
                                >Correo Electrónico de Contacto</label
                            >
                            <input
                                type="email"
                                id="correo"
                                name="correo"
                                value="<%= clienteToEdit.getCorreo() %>"
                                required />
                        </div>

                        <div style="display: flex; gap: 1rem">
                            <div class="form-group" style="flex: 1">
                                <label for="telefono">Número Telefónico</label>
                                <input
                                    type="tel"
                                    id="telefono"
                                    name="telefono"
                                    value="<%= clienteToEdit.getTelefono() %>"
                                    required />
                            </div>
                            <div class="form-group" style="flex: 1">
                                <label for="saldo">Modificar Saldo ($)</label>
                                <input
                                    type="number"
                                    id="saldo"
                                    name="saldo"
                                    step="0.01"
                                    value="<%= clienteToEdit.getSaldo() %>"
                                    required />
                            </div>
                        </div>

                        <div style="display: flex; gap: 1rem; margin-top: 2rem">
                            <button
                                type="submit"
                                class="btn-primary"
                                style="margin-top: 0">
                                Guardar Cambios
                            </button>
                            <button
                                type="button"
                                class="btn-danger"
                                style="margin-top: 0"
                                onclick="
                                    if (
                                        confirm(
                                            '¿Seguro que desea eliminar esta cuenta? Esta acción es irreversible.',
                                        )
                                    ) {
                                        window.location =
                                            'DeleteClientServlet?id=<%= clienteToEdit.getId() %>';
                                    }
                                ">
                                Eliminar Cuenta
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </main>

        <script src="js/homePage-script.js"></script>
    </body>
</html>
