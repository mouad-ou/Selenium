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

public class PriceComparison {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*", "--disable-blink-features=AutomationControlled", "--disable-web-security");
        WebDriver driver = new ChromeDriver(options);
        String product = "Iphone 15" +
                "";

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            driver.get("https://www.jumia.ma/");
            WebElement searchBox1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
            searchBox1.sendKeys(product);
            searchBox1.submit();
            WebElement jumiaPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.prc")));
            String jumiaPrice = jumiaPriceElement.getText();
            System.out.println("Jumia Price: " + jumiaPrice);

            driver.get("https://www.electroplanet.ma/");
            WebElement searchBox2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
            searchBox2.sendKeys(product);
            searchBox2.submit();
            WebElement electroplanetPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.price-wrapper span.price")));
            String electroplanetPrice = electroplanetPriceElement.getText().trim() + " " + driver.findElement(By.cssSelector("span.price-wrapper span.currency")).getText().trim();
            System.out.println("Electroplanet Price: " + electroplanetPrice);

            double jumiaPriceValue = Double.parseDouble(jumiaPrice.replaceAll("[^0-9.]", "").replace(",", "."));
            double electroplanetPriceValue = Double.parseDouble(electroplanetPrice.replaceAll("[^0-9.]", "").replace(" ", "").replace(",", "."));

            if (jumiaPriceValue < electroplanetPriceValue) {
                System.out.println("Jumia has the lower price: " + jumiaPrice);
            } else if (jumiaPriceValue > electroplanetPriceValue) {
                System.out.println("Electroplanet has the lower price: " + electroplanetPrice);
            } else {
                System.out.println("Both Jumia and Electroplanet have the same price: " + jumiaPrice);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
