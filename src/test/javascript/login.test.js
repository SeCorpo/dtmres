// login.test.js

const loginModule = require('../../main/resources/static/js/login');

describe('Login functionality', () => {
    beforeAll(() => {
        document.body.innerHTML = `
            <input id="login-email" type="text" />
            <input id="login-password" type="password" />
            <button id="login-button">Login</button>
            <img id="show-password" src="" />
            <div id="error-message" style="display: none;"></div>
        `;
        loginModule.initializeDOMElements();
    });

    it('should toggle password visibility', () => {
        loginModule.togglePasswordVisibility();
    });

    it('should hide error message', () => {
        loginModule.hideErrorMessage();
    });
    it('should handle login button click for successful login', async () => {
        // Mock loginCheck function to return true for successful login
        jest.spyOn(loginModule, 'loginCheck').mockImplementation(async () => true);
    
        loginModule.handleLoginButtonClick();
        await loginModule.loginButton.click();
    });
    
    it('should handle login button click for failed login', async () => {
        jest.spyOn(loginModule, 'loginCheck').mockImplementation(async () => false);
    
        loginModule.handleLoginButtonClick();
        await loginModule.loginButton.click();

    });
    it('should hide error message when typing in email input', () => {
        loginModule.loginEmail.value = 'test@example.com';
        loginModule.hideErrorMessage();
        loginModule.loginEmail.dispatchEvent(new Event('input'));
    
    });
    
    it('should hide error message when typing in password input', () => {
        loginModule.loginPassword.value = 'testpassword';
        loginModule.hideErrorMessage();
        loginModule.loginPassword.dispatchEvent(new Event('input'));
    });
    
    it('should trigger login on "Enter" key press', async () => {
        
        jest.spyOn(loginModule, 'loginCheck').mockImplementation(async () => true);
    
        const enterKeyEvent = new KeyboardEvent('keypress', { key: 'Enter' });
        loginModule.handleLoginButtonClick();
        loginModule.loginEmail.dispatchEvent(enterKeyEvent);
    });
    
    
});
