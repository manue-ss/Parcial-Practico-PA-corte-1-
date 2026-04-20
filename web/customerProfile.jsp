<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="co.edu.udistrital.model.entities.Cliente" %>
<!doctype html>
<html lang="es">
<head>
    <title>Mi Perfil - RentZone</title>
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
            <span>Gestión de Cuenta</span>
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
                    <li class="menu-item active menu-item-static">
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

        <div class="page-content profile-page-content">
            <% 
                Cliente c = (Cliente) session.getAttribute("usuarioLogueado");
                if (c == null) { 
                    response.sendRedirect("index.jsp"); 
                    return; 
                }
            %>
            <!-- Panel izquierdo: Saldo y Membresía -->
            <div class="action-box panel-side">
                <h2 class="action-box-title">Estado Financiero</h2>
                
                <div class="balance-card">
                    <h3>Saldo Disponible</h3>
                    <div class="amount">$ <%= c.getSaldo() %></div>
                </div>

                <form action="RechargeBalanceServlet" method="POST" class="bottom-margin-md">
                    <div class="form-group">
                        <label for="montoRecarga">Monto a Recargar ($)</label>
                        <div class="inline-input-btn">
                            <input type="number" id="montoRecarga" name="monto" min="1" step="0.01" required>
                            <button type="submit" class="btn-primary">Recargar</button>
                        </div>
                    </div>
                </form>

                <h2 class="action-box-title top-margin-md">Membresía</h2>
                <form action="UpdateMembershipServlet" method="POST">
                    <div class="form-group">
                        <label for="tipoMembresia">Nivel Actual</label>
                        <select id="tipoMembresia" name="membresia">
                            <option value="NORMAL" <%= c.getMembresia().name().equals("NORMAL") ? "selected" : "" %>>Normal</option>
                            <option value="SILVER" <%= c.getMembresia().name().equals("SILVER") ? "selected" : "" %>>Silver (Beneficios extra)</option>
                            <option value="GOLD" <%= c.getMembresia().name().equals("GOLD") ? "selected" : "" %>>Gold (Envío gratis)</option>
                        </select>
                    </div>
                    <button type="submit" class="btn-primary">Actualizar Plan</button>
                </form>
            </div>

            <!-- Panel derecho: Datos Personales (Cuenta) -->
            <div class="action-box panel-main">
                <h2 class="action-box-title">Datos Personales</h2>
                <form action="UpdateProfileServlet" method="POST">
                    <div class="form-group">
                        <label for="nombreCompleto">Nombre Completo</label>
                        <input type="text" id="nombreCompleto" name="nombreCompleto" value="<%= c.getNombreCompleto() %>" required>
                    </div>
                    <div class="form-group">
                        <label for="nombreUsuario">Usuario (Nickname)</label>
                        <input type="text" id="nombreUsuario" name="nombreUsuario" value="<%= c.getNombreUsuario() %>" readonly class="readonly-input">
                    </div>
                    <div class="form-group">
                        <label for="correo">Correo Electrónico</label>
                        <input type="email" id="correo" name="correo" value="<%= c.getCorreo() %>" required>
                    </div>
                    <div class="form-group">
                        <label for="telefono">Teléfono</label>
                        <input type="tel" id="telefono" name="telefono" value="<%= c.getTelefono() %>" required>
                    </div>
                    <button type="submit" class="btn-primary">Guardar Cambios</button>
                </form>
            </div>
            
        </div>
    </main>
    <script src="js/homePage-script.js"></script>
</body>
</html>
