<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="es">
    <head>
        <title>Login</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="css/login-style.css" />
    </head>
    <body>
        <article class="card">
            <section class="card-form">
                <form
                    class="form-login"
                    action="LoginServlet"
                    method="post"
                    role="form"
                    aria-label="Inicio de sesión">
                    <h1>Inicio de sesión</h1>
                    <span>Use su correo electrónico o nombre de usuario</span
                    >
                    <span class="mensaje-error">
                        <%
                            String errorLoginMessage = (String) request.getAttribute("errorLoginMessage");
                            if (errorLoginMessage != null) {
                                out.println(errorLoginMessage);
                            }
                        %>
                    </span>
                    <div class="input-container">
                        <ion-icon name="person-circle-outline"></ion-icon>
                        <input
                            type="text"
                            id="username-login"
                            name="username"
                            placeholder="Usuario o Correo Electrónico"
                            required />
                    </div>
                    <div class="input-container">
                        <ion-icon name="lock-closed-outline"></ion-icon>
                        <input
                            type="password"
                            id="password-login"
                            name="password"
                            placeholder="Contraseña"
                            required />
                    </div>
                    <a href="development.html">¿Olvidaste tu contraseña?</a>
                    <input
                        type="submit"
                        value="Iniciar Sesión"
                        class="button" />
                </form>
            </section>
            <section class="card-form">
                <form
                    class="form-signup"
                    action="SignupServlet"
                    method="post"
                    role="form"
                    aria-label="Registro de usuario">
                    <h1>Registro</h1>
                    <span>Usa tus datos personales para registrarte</span>
                    <span class="mensaje-error">
                        <%
                        String errorSignupMessage = (String) request.getAttribute("errorSignupMessage");
                        if (errorSignupMessage != null) {
                            out.println(errorSignupMessage);
                        }
                        %>
                    </span>
                    <span class="mensaje-exito">
                        <%
                        String successMessage = (String) request.getAttribute("successMessage");
                        if (successMessage != null) {
                            out.println(successMessage);
                        }
                        %>
                    </span>
                    <div class="input-container">
                        <ion-icon name="person-outline"></ion-icon>
                        <input
                            type="text"
                            id="name"
                            name="name"
                            placeholder="Nombre Completo"
                            required />
                    </div>
                    <div class="input-container">
                        <ion-icon name="person-circle-outline"></ion-icon>
                        <input
                            type="text"
                            id="username-signup"
                            name="username"
                            placeholder="Nombre de Usuario"
                            required />
                    </div>
                    <div class="input-container">
                        <ion-icon name="mail-outline"></ion-icon>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            placeholder="Correo electrónico"
                            required />
                    </div>
                    <div class="input-container">
                        <ion-icon name="call-outline"></ion-icon>
                        <input
                            type="tel"
                            id="phone"
                            name="phone"
                            pattern="[0-9+\-\s]{7,15}"
                            placeholder="Número de teléfono"
                            required />
                    </div>

                    <div class="input-container">
                        <ion-icon name="lock-closed-outline"></ion-icon>
                        <input
                            type="password"
                            id="password-signup"
                            name="password"
                            placeholder="Contraseña"
                            required />
                    </div>

                    <input type="submit" value="Registrarse" class="button" />
                </form>
            </section>

            <section class="card-welcome">
                <div class="welcome-signup welcome">
                    <h3>¡Hola Denuevo!</h3>
                    <p>
                        Si es tu primera vez, registrate con tus datos
                        personales
                    </p>
                    <button class="button" id="btn-signup">Registrarse</button>
                </div>
                <div class="welcome-login welcome">
                    <h3>¡Bienvenido!</h3>
                    <p>
                        ¿Ya eres un usuario de nuestro servicio?, inicia sesión
                        con tus datos para ingresar
                    </p>
                    <button class="button" id="btn-login">
                        Iniciar Sesión
                    </button>
                </div>
            </section>
        </article>

        <script
            type="module"
            src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
        <script
            nomodule
            src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
        <script src="js/login-script.js"></script>
    </body>
</html>
