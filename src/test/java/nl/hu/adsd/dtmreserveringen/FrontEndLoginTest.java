package nl.hu.adsd.dtmreserveringen;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static org.junit.Assert.*;

public class FrontEndLoginTest {

    ////////////////////////////////////////////////////
    //Start the application manually
    ////////////////////////////////////////////////////

    private static final Logger logger = LoggerFactory.getLogger(FrontEndLoginTest.class);

    @Test
    public void testSuccessfulLogin() {
        // Initialize WebDriver and navigate to the login page
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "false"); //hides ugly red unnecessary/irrelevant messages
        WebDriver driver = new FirefoxDriver();
        driver.get("http://localhost:8080/login");

        // Locate form elements
        WebElement usernameInput = driver.findElement(By.id("login-email"));
        WebElement passwordInput = driver.findElement(By.id("login-password"));
        WebElement submitButton = driver.findElement(By.id("login-button"));

        // Enter test data
        usernameInput.sendKeys("piet@student.hu.nl");
        passwordInput.sendKeys("Wachtwoord@123");

        // Submit the form
        submitButton.submit();

        // Wait for 2 seconds after submitting the form
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.debug(e.getMessage());
        }

        // Check if a new jsession cookie was created
        boolean foundJsession = checkJsessionCookie(driver);

        // Check redirection to desired page
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "http://localhost:8080/";

        // Assert that the user is redirected to the desired page
        assertEquals(expectedUrl, currentUrl);

        // Assert that a new jsession cookie was not created
        assertTrue(foundJsession);

        // Close the browser
        driver.close();
    }

    @Test
    public void testFailedLogin() {
        // Initialize WebDriver and navigate to the login page
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "false");
        WebDriver driver = new FirefoxDriver();
        driver.get("http://localhost:8080/login");

        // Locate form elements
        WebElement usernameInput = driver.findElement(By.id("login-email"));
        WebElement passwordInput = driver.findElement(By.id("login-password"));
        WebElement submitButton = driver.findElement(By.id("login-button"));

        // Enter invalid login data
        usernameInput.sendKeys("invalid_username");
        passwordInput.sendKeys("invalid_password");

        // Submit the form
        submitButton.submit();

        // Wait for 2 seconds after submitting the form
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.debug(e.getMessage());
        }

        // Check if a new jsession cookie was created
        boolean foundJsession = checkJsessionCookie(driver);

        // Check redirection to desired page
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "http://localhost:8080/login?error";

        // Assert that the user is redirected to the desired page
        assertEquals(expectedUrl, currentUrl);

        // Assert that a new jsession cookie was not created
        assertTrue(foundJsession);

        // Close the browser
        driver.close();
    }
    private boolean checkJsessionCookie(WebDriver driver) {
        Set<Cookie> cookies = driver.manage().getCookies();

        boolean foundJsession = false;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("JSESSIONID")) {
                foundJsession = true;
                break;
            }
        }

        return foundJsession;
    }
}
