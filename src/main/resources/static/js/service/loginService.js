export async function loginCheck(email, password) {

    console.log("loginService reached")
    // try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                // 'Accept': 'application/json',
                // 'Content-Type': 'application/json'
            },
            body: JSON.stringify({username: email, password: password}),
        });
        if (!response.ok) {
            console.log("Response not OK " + response.status);
            alert("Er is een fout opgetreden bij het verifiëren van het wachtwoord. Probeer het opnieuw");
            return null;
        // }

        // const json = await response.json(); // Read the response body once
        //
        // return json;
    // } catch(error) {
    //     console.error("Error during login request:", error);
    //
    //     if (error instanceof SyntaxError) {
    //         // Response is not JSON
    //         alert("Invalid response format from the server");
    //     } else if (error instanceof TypeError) {
    //         // Handle network errors
    //         alert("Network error. Please check your internet connection.");
    //     } else {
    //         // Handle other errors
    //         alert("An error occurred. Please try again.");
    //     }
    //
    //     throw error;
    }
}