export async function loginCheck(email, password) {

    console.log("loginService reached")
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