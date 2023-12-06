class Session {

    currentSession;
    // constructor() {
    //     this.currentSession = null;
    // }

    //getSessionFromCookie
    getSession() {
        const allCookies = document.cookie.split(';');
        for(const cookie of allCookies) {
            const [name, value] = cookie.trim().split('=');

            if(name === 'currentSession') {
                return decodeURIComponent(value);
            }
        }
        // send alert if currentSession is not found in a cookie
        alert("Please login first, to get a session");
    }

    //setSessionToCookie named currentSession
    // TODO: should the session be sent on every server request? Or only when making a reservation?
    setSession(session) {
        const expirationDate = new Date();
        expirationDate.setTime(expirationDate.getTime() + (30 * 60 * 1000)); // 30 minutes

        const expires = "expires=" + expirationDate.toUTCString();
        document.cookie = `${this.currentSession}=${session}; path=/; ${expires}`;
    }

}

