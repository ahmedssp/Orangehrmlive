package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.ConfigReader;

import java.time.Duration;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Initialize driver
    public static void initDriver() {

        String browser = System.getProperty("browser");

        // If no system property, take from YAML
        if (browser == null) {
            browser = ConfigReader.getBrowser();
        }

        switch (browser.toLowerCase()) {

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();

                if (ConfigReader.isHeadless()) {
                    chromeOptions.addArguments("--headless=new");
                }

                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (ConfigReader.isHeadless()) {
                    firefoxOptions.addArguments("--headless");
                }

                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            case "edge":
                driver.set(new EdgeDriver());
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));

        getDriver().manage().window().maximize();
        getDriver().get(ConfigReader.getUrl());
    }

    // Get driver (Thread safe)
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Quit driver
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}