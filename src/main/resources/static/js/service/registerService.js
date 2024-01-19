export async function registerUser(email, password){

    try {
        console.log("registerService reached")
        return await fetch("/api/auth/register", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username: email, password: password}),
        });
    } catch (e) {
        console.log("Unable to make a post request to the server\n " + e.message)
    }
}