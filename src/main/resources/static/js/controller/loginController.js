const loginEmail = document.getElementById("login-email");
const loginPassword = document.getElementById("login-password");
const errorMessage = document.getElementById("error-message");
const showPassword = document.getElementById("show-password");
const loginButton = document.getElementById("login-button")

loginEmail.addEventListener("input", () => hideErrorMessage());
loginPassword.addEventListener("input", () => hideErrorMessage());
loginPassword.addEventListener("keypress", (enter) => handleKeyPress(enter));
showPassword.addEventListener('click', () => handleTogglePassword())

function hideErrorMessage() {
    errorMessage.style.display = 'none';
}
function showErrorMessage(message) {
    errorMessage.textContent = message;
    errorMessage.style.display = 'block';
}

function handleTogglePassword() {
    const isPasswordVisible = loginPassword.getAttribute('type') === 'text';
    loginPassword.setAttribute('type', isPasswordVisible ? 'password' : 'text');
    showPassword.src = isPasswordVisible
        ? '../icons/eye-outline.svg'
        : '../icons/eye-off-outline.svg';
}

function handleKeyPress(event) {
    if(event.key === "Enter") {
        event.preventDefault();
        loginButton.click();
    }
}
