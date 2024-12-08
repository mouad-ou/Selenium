package Mine;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileOutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RechercheEntreprises {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\users\\mouad\\Downloads\\chromedriver-win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.indeed.com/");
            driver.manage().window().maximize();

            System.out.println("Veuillez compléter la vérification CAPTCHA.");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("text-input-what")));

            WebElement secteurInput = driver.findElement(By.id("text-input-what"));
            secteurInput.sendKeys("Informatique");

            WebElement regionInput = driver.findElement(By.id("text-input-where"));
            regionInput.clear();
            regionInput.sendKeys("Casablanca");

            WebElement searchButton = driver.findElement(By.cssSelector("button[type='submit']"));
            searchButton.click();

            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".job_seen_beacon")));

            List<WebElement> results = driver.findElements(By.cssSelector(".job_seen_beacon"));

            List<String[]> jobData = new ArrayList<>();

            for (WebElement result : results) {
                String nom = result.findElement(By.cssSelector(".company_location span:first-child")).getText();
                String titre = result.findElement(By.cssSelector(".jobTitle span")).getText();
                String localisation = result.findElement(By.cssSelector(".company_location div")).getText();

                // Ajouter les données dans la liste
                jobData.add(new String[]{nom, titre, localisation});
            }

            // Sauvegarder dans un fichier Excel
            saveToExcel(jobData);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static void saveToExcel(List<String[]> jobData) {
        String[] headers = {"Nom de l'entreprise", "Titre du poste", "Localisation"};
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Résultats de recherche");

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (String[] rowData : jobData) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < rowData.length; i++) {
                row.createCell(i).setCellValue(rowData[i]);
            }
        }

        try (FileOutputStream fos = new FileOutputStream("./Résultats_Recherche.xlsx")) {
            workbook.write(fos);
            System.out.println("Les résultats ont été sauvegardés dans 'Résultats_Recherche.xlsx'.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
