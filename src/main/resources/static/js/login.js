const loginButton = document.getElementById("login-button")
const loginEmail = document.getElementById("login-email");
const loginPassword = document.getElementById("login-password");


// Hide the error message when the user starts typing
loginEmail.addEventListener("input", () => {
    document.getElementById("error-message").style.display = 'none';
});
loginPassword.addEventListener("input", () => {
    document.getElementById("error-message").style.display = 'none';
});
loginButton.addEventListener("click", async e => {
    e.preventDefault();
    let email = loginEmail.value;
    let password = loginPassword.value;

    if(await loginCheck(email, password)) {
        console.log("Admin login successful")
        window.location.href = '/admin';
    } else {
        loginPassword.value = "";
        document.getElementById("error-message").style.display = 'block';
    }
})


async function loginCheck(email, password) {
    if(email.trim() === '' || password.trim() === '') {
        console.error("Email or password is null, undefined, or empty");
        return false;
    }
    try {
        const response = await fetch('/api/login/login', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({email: email, password: password}),
        });

        if (!response.ok) {
            console.log("All reservations: response is error; Status code: " + response.status);
            alert("Er is een fout opgetreden bij het verifiëren van het wachtwoord. Probeer het opnieuw");
            return false;
        } else {
            return await response.json();

        }
    } catch(error) {
        console.error("Error during login request:", error);
        return false;
    }
}
