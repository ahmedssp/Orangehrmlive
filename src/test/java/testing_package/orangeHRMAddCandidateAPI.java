package testing_package;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class orangeHRMAddCandidateAPI {

    private WebDriver driver;
    private Map<String, String> sessionCookies;

    @BeforeClass
    public void setup() {
        // Setup ChromeDriver (make sure chromedriver executable is in PATH or provide its location)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);

        // Configure implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Base URI for RestAssured
        RestAssured.baseURI = "https://opensource-demo.orangehrmlive.com";
    }

    @Test(priority = 1)
    public void testLoginWithSelenium() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Enter username
        driver.findElement(By.name("username")).sendKeys("Admin");
        // Enter password
        driver.findElement(By.name("password")).sendKeys("admin123");
        // Click login button
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Wait for dashboard to load and check if login is successful by checking dashboard element
        boolean isDashboardVisible = driver.findElements(By.cssSelector("div.orangehrm-dashboard")).size() > 0;
        Assert.assertTrue(isDashboardVisible, "Login failed or dashboard not visible.");

        // Extract cookies for API use
        sessionCookies = new HashMap<>();
        Set<Cookie> seleniumCookies = driver.manage().getCookies();
        for (Cookie cookie : seleniumCookies) {
            sessionCookies.put(cookie.getName(), cookie.getValue());
        }
        Assert.assertTrue(sessionCookies.containsKey("orangehrm"), "Session cookie 'orangehrm' not found.");
    }

    @Test(priority = 2, dependsOnMethods = "testLoginWithSelenium")
    public void testAddCandidateViaAPI() {
        // Prepare API payload (example to add candidate)
        String payload = """
        {
          "firstName": "John",
          "middleName": "M",
          "lastName": "Doe",
          "email": "john.doe@example.com",
          "contactNo": "1234567890",
          "vacancyId": 1
        }
        """;

        Response response = RestAssured
                .given()
                .cookies(sessionCookies)  // Pass Selenium cookies here
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/web/index.php/api/v2/recruitment/candidates")
                .then()
                .extract().response();

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200, "Failed to add candidate via API.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}