package Pages;
import Handler.synchronization_methods;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import static Handler.synchronization_methods.WaitForvisibilityOfElementLocated;
import static Handler.synchronization_methods.waitFor_Element_toBe_clickable;

public class P0_LoginPage {

    protected ExtentReports extent;
    protected ExtentSparkReporter spark;
    ExtentTest logger;

    WebDriver driver;

    // Locators
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//button[@type='submit']");

    // Constructor
    public P0_LoginPage(WebDriver driver) {
        this.driver = driver;
    }


    public void enter_username(String username) {
        try {
            WaitForvisibilityOfElementLocated(driver, usernameField);
            driver.findElement(usernameField).clear();
            driver.findElement(usernameField).sendKeys(username);
        } catch (Exception e) {
            driver.navigate().refresh();
            WaitForvisibilityOfElementLocated(driver, usernameField);
            driver.findElement(usernameField).sendKeys(username);
        }
    }

    public void enter_password(String password) {
        try {
            synchronization_methods.WaitForvisibilityOfElementLocated(driver, passwordField);
            driver.findElement(passwordField).clear();
            driver.findElement(passwordField).sendKeys(password);
        } catch (Exception e) {
            driver.navigate().refresh();
            WaitForvisibilityOfElementLocated(driver, passwordField);
            driver.findElement(passwordField).sendKeys(password);
        }
    }



    // Combined login
    public void login(String username, String password) {
        enter_username(username);
        enter_password(password);

    }
    public String actualUrl(){
        synchronization_methods.waitFor_url_containing(driver, "/dashboard/index");
      return   driver.getCurrentUrl();
    }
    public Pages.P1_Dashbord click_login_button() {
        try {
            waitFor_Element_toBe_clickable(driver, loginButton);
            Actions ac = new Actions(driver);
            ac.moveToElement(driver.findElement(loginButton)).click().build().perform();
            return new Pages.P1_Dashbord(driver);
        } catch (Exception e) {
            driver.navigate().refresh();
            waitFor_Element_toBe_clickable(driver, loginButton);
            Actions ac = new Actions(driver);
            ac.moveToElement(driver.findElement(loginButton)).click().build().perform();
            return new Pages.P1_Dashbord(driver);
        }
    }
}
