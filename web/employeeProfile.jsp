<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="co.edu.udistrital.model.entities.Empleado" %>
<!doctype html>
<html lang="es">
<head>
    <title>Perfil Empleado - RentZone Staff</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/base-layout.css" />
    <link rel="stylesheet" href="css/employeeHomePage-style.css" />
    <link rel="stylesheet" href="css/homePage-style.css" /> <!-- Para action-box y form-group -->
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
            <span>Mi Perfil Córporativo</span>
        </div>
        <div class="header-actions">
            <a href="employeeProfile.jsp" class="header-icon" title="Perfil Empleado"><i class='bx bxs-user-badge'></i></a>
            <a href="EmployeeLogoutServlet" class="header-icon" title="Cerrar Sesión"><i class='bx bx-log-out'></i></a>
        </div>
    </header>
    
    <main class="main">
        <div class="sidebar" id="sidebar">
            <div class="menu-container">
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
                    <li class="menu-item active menu-item-static">
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
            <% 
                Empleado e = (Empleado) session.getAttribute("empleadoLogueado");
                if (e == null) { 
                    response.sendRedirect("employeeLogin.jsp"); 
                    return; 
                }
            %>
            <div class="action-box">
                <h2 class="action-box-title">Datos Laborales y Credenciales</h2>
                
                <form action="UpdateEmployeeProfileServlet" method="POST">
                    
                    <div class="identity-badge">
                        <i class='bx bx-id-card identity-badge-icon'></i>
                        <div class="identity-badge-info">
                            <span class="identity-badge-primary">DNI: <%= e.getDni() %></span>
                            <span class="identity-badge-secondary">Cargo: <%= e.getId().startsWith("Ad") ? "Administrador Jefe" : "Empleado de Tienda" %></span>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="nombreUsuario">Usuario de Acceso en Sistema</label>
                            <input type="text" id="nombreUsuario" name="nombreUsuario" value="<%= e.getNombreUsuario() %>" readonly class="readonly-input">
                        </div>
                        <div class="form-group">
                            <label for="contrasenia">Modificar Contraseña</label>
                            <input type="password" id="contrasenia" name="contrasenia" placeholder="Dejar vacío para no cambiar">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="nombreCompleto">Nombre Completo</label>
                        <input type="text" id="nombreCompleto" name="nombreCompleto" value="<%= e.getNombreCompleto() %>" required>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="correo">Correo Electrónico Institucional</label>
                            <input type="email" id="correo" name="correo" value="<%= e.getCorreo() %>" required>
                        </div>
                        <div class="form-group">
                            <label for="telefono">Teléfono de Contacto</label>
                            <input type="tel" id="telefono" name="telefono" value="<%= e.getTelefono() %>" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="direccion">Dirección Residencial Física</label>
                        <input type="text" id="direccion" name="direccion" value="<%= e.getDireccion() %>" required>
                    </div>

                    <button type="submit" class="btn-primary">Guardar Mis Cambios</button>
                    
                </form>
            </div>
        </div>
    </main>

    <script src="js/homePage-script.js"></script>
</body>
</html>
