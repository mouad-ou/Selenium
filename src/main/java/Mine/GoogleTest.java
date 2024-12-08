package Mine;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
public class GoogleTest {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*", "--disable-blink-features=AutomationControlled", "--disable-web-security");
        WebDriver driver = new ChromeDriver(options);
        String product = "Iphone 15"    ;

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            driver.get("https://www.google.com");
            WebElement searchBox1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
            searchBox1.sendKeys(product);
            searchBox1.submit();
            WebElement jumiaPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rso")));
            String jumiaPrice = jumiaPriceElement.getText();
            System.out.println("Jumia Price: " + jumiaPrice);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
