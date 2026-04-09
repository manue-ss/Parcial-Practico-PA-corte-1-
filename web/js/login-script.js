const buttonLogin = document.querySelector("#btn-login");
const buttonSignup = document.querySelector("#btn-signup");
const contenedor = document.querySelector(".card");

if (buttonLogin) {
    buttonLogin.addEventListener("click", () => {
        console.log("click-login");
        contenedor.classList.remove("toggle");
    });
} else {
    console.error("Elemento #btn-login no encontrado");
}

if (buttonSignup) {
    buttonSignup.addEventListener("click", () => {
        console.log("click-signup");
        contenedor.classList.add("toggle");
    });
} else {
    console.error("Elemento #btn-signup no encontrado");
}

