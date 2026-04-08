const buttonLogin = document.querySelector("#btn-login");
const buttonSingup = document.querySelector("#btn-singup");
const contenerdor = document.querySelector(".card");

buttonLogin.addEventListener("click", () => {
    contenerdor.classList.remove("toggle");
});

buttonSingup.addEventListener("click", () => {
    contenerdor.classList.add("toggle");
});

