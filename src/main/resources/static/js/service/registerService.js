export async function registerUser(email, password){

    console.log("registerService reached")
    try {
        const response = await fetch("/api/account/add", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({email: email, password: password}),
        });

        if (!response.ok) {
            console.log("Response not OK " + response.status);
            alert("Er is een fout opgetreden bij het registreren van het account. Probeer het opnieuw");
            return false;
        } else {
            return await response.json()
        }
    } catch (error) {
        console.error("Error during register request:", error);
        return false;
    }
}