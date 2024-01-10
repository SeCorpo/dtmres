import {registerUser} from "../service/registerService.js";

const registerEmail = document.getElementById("login-email");
const registerPassword = document.getElementById("login-password");
const registerRepeatPassword = document.getElementById("repeat-password");
const registerButton = document.getElementById("register-button");

//TODO: add show-password button (from login-page)

registerButton.addEventListener("click", async e => {
    e.preventDefault();
    let email = registerEmail.value;
    let password = registerPassword.value;
    let confirmPassword = registerRepeatPassword.value;


    if (!validateEmail(email)) {
        alert("Voer een geldig e-mailadres in.");
        return;
    }

    if (!validatePassword(password)) {
        alert("Voer een wachtwoord in van minimaal 8 tekens + een cijfer.");
        return;
    }

    if (password !== confirmPassword) {
        alert("Wachtwoorden komen niet overeen.");
        return;
    }

    try {
        const response = await registerUser(email, password);
        if(response.ok) {
            //todo: meldingen met Toastify
            alert("Het account met email: " + email + " is aangemaakt\n Login alstublief")
            window.location.replace("/login");
        }
        alert(response.status + ": " + await response.text())

    } catch(e) {
        alert("Unable to reach the register service\n " + e.message);
    }
});

function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(String(email).toLowerCase());
}

function validatePassword(password) {
    const re = /^(?=.*[0-9])[a-zA-Z0-9!@#$%^&*]{8,16}$/;
    return re.test(password);
}