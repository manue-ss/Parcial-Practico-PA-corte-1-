<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="es">
<head>
    <title>Portal Empleados - Acceso</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/employee-login.css" />
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
</head>
<body>
    <div class="login-container">
        <h1>Área de Empleados</h1>
        <p>Inicie sesión con sus credenciales institucionales.</p>

        <span class="error-message">
            <%
                String errorMsg = (String) request.getAttribute("errorLoginMessage");
                if (errorMsg != null) {
                    out.println(errorMsg);
                }
            %>
        </span>

        <form action="EmployeeLoginServlet" method="post">
            <div class="input-group">
                <i class='bx bx-user'></i>
                <input type="text" name="username" placeholder="Nombre de Usuario" required />
            </div>
            <div class="input-group">
                <i class='bx bx-lock-alt'></i>
                <input type="password" name="password" placeholder="Contraseña" required />
            </div>
            <button type="submit" class="btn-primary">Ingresar</button>
        </form>
    </div>

</body>
</html>
