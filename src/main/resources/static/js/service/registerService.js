export async function registerUser(email, password){

    console.log("registerService reached")
    return await fetch("/api/auth/register", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({username: email, password: password}),
    });
}