package Mine;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ConnexionTest {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*", "--disable-blink-features=AutomationControlled", "--disable-web-security");
        WebDriver driver = new ChromeDriver(options);

        try {
            testEmptyUsername(driver);

            testEmptyPassword(driver);

            testValidCredentials(driver);

            testInvalidCredentials(driver);

            testEmptyBothFields(driver);
        } finally {
            driver.quit();
        }
    }

    private static void testEmptyUsername(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/connexion.html");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("password");
        WebElement loginButton = driver.findElement(By.cssSelector("button"));
        loginButton.click();

        WebElement loginMessage = driver.findElement(By.id("loginMessage"));
        String message = loginMessage.getText();
        if (message.equals("Nom d'utilisateur ou mot de passe incorrect.")) {
            System.out.println("Test Empty Username: Passed");
        } else {
            System.out.println("Test Empty Username: Failed");
        }
    }

    private static void testEmptyPassword(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/connexion.html");
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("user");
        WebElement loginButton = driver.findElement(By.cssSelector("button"));
        loginButton.click();

        WebElement loginMessage = driver.findElement(By.id("loginMessage"));
        String message = loginMessage.getText();
        if (message.equals("Nom d'utilisateur ou mot de passe incorrect.")) {
            System.out.println("Test Empty Password: Passed");
        } else {
            System.out.println("Test Empty Password: Failed");
        }
    }

    private static void testValidCredentials(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/connexion.html");
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("user");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("password");
        WebElement loginButton = driver.findElement(By.cssSelector("button"));
        loginButton.click();

        WebElement loginMessage = driver.findElement(By.id("loginMessage"));
        String message = loginMessage.getText();
        if (message.equals("Connexion réussie !")) {
            System.out.println("Test Valid Credentials: Passed");
        } else {
            System.out.println("Test Valid Credentials: Failed");
        }
    }

    private static void testInvalidCredentials(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/connexion.html");
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("user");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("wrongpass");
        WebElement loginButton = driver.findElement(By.cssSelector("button"));
        loginButton.click();

        WebElement loginMessage = driver.findElement(By.id("loginMessage"));
        String message = loginMessage.getText();
        if (message.equals("Nom d'utilisateur ou mot de passe incorrect.")) {
            System.out.println("Test Invalid Credentials: Passed");
        } else {
            System.out.println("Test Invalid Credentials: Failed");
        }
    }

    private static void testEmptyBothFields(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/connexion.html");
        WebElement loginButton = driver.findElement(By.cssSelector("button"));
        loginButton.click();

        WebElement loginMessage = driver.findElement(By.id("loginMessage"));
        String message = loginMessage.getText();
        if (message.equals("Nom d'utilisateur ou mot de passe incorrect.")) {
            System.out.println("Test Empty Both Fields: Passed");
        } else {
            System.out.println("Test Empty Both Fields: Failed");
        }
    }
}
