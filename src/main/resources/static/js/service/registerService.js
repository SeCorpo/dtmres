export async function registerUser(email, password){

    console.log("registerService reached")
    try {
        const response = await fetch("/api/auth/register", {
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
            return null;
        }

        return await response.json()

    } catch(error) {
        console.error("Error during register request:", error);

        if (error instanceof SyntaxError) {
            // Response is not JSON
            alert("Invalid response format from the server");
        } else if (error instanceof TypeError) {
            // Handle network errors
            alert("Network error. Please check your internet connection.");
        } else {
            // Handle other errors
            alert("An error occurred. Please try again.");
        }

        throw error;
    }
}