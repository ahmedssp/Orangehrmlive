package Pages;

import Handler.synchronization_methods;
import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class P1_Dashbord {

    WebDriver driver;
    WebDriverWait wait;
    Faker faker = new Faker();

    // Constructor
    public P1_Dashbord(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ------------------- Locators -------------------
    private By adminMenu = By.xpath("//span[text()='Admin']");
    private By addButton = By.xpath("//button[normalize-space()='Add']");
    private By recordCount = By.xpath("//*[contains(*,\"Records Found\")]/span");
    private By userRows = By.xpath("//div[@role='table']//div[@role='rowgroup']/div");
    private By ResetBottom =By.xpath("//button[normalize-space()='Reset']");


    // Add User Form
    private By userRoleDropdown = By.xpath("//label[text()='User Role']/../following-sibling::div//div[contains(@class,'oxd-select-text')]");
    private By statusDropdown = By.xpath("//label[text()='Status']/../following-sibling::div//div[contains(@class,'oxd-select-text')]");
    private By employeeNameField = By.xpath("//input[@placeholder='Type for hints...']");
    private By usernameField = By.xpath("//label[text()='Username']/../following-sibling::div/input");
    private By passwordField = By.xpath("//label[text()='Password']/../following-sibling::div/input");
    private By confirmPasswordField = By.xpath("//label[text()='Confirm Password']/../following-sibling::div/input");
    private By saveButton = By.xpath("//button[normalize-space()='Save']");
    private By alreadyExistsMsg = By.xpath("//span[contains(.,'exists')]");
    private By Listbox=By.xpath("//div[@role='listbox']");

    // Search and Delete
    private By searchUsernameField = By.xpath("//label[text()='Username']/../following-sibling::div/input");
    private By searchButton = By.xpath("//button[normalize-space()='Search']");
    private By deleteButton = By.xpath("//i[contains(@class,'bi-trash')]");
    private By confirmDeleteButton = By.xpath("//button[normalize-space()='Yes, Delete']");
    private By dropdownOptions = By.xpath("//div[@role='option']//span");

    // ------------------- Actions -------------------

    // Navigate to Admin tab
    public void goToAdminTab() {
        try {
            synchronization_methods.WaitForvisibilityOfElementLocated(driver, adminMenu);
            driver.findElement(adminMenu).click();

        }catch (Exception e){
            synchronization_methods.waitFor_Element_toBe_clickable(driver,adminMenu);
            driver.findElement(adminMenu).click();
        }

    }

    // Get total record count
    public int getRecordCount() {
        synchronization_methods.WaitForvisibilityOfElementLocated(driver, recordCount);
        String text = driver.findElement(recordCount).getText(); // e.g. "(36) Records Found"
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }
    public void enterUniqueUsername(WebDriver driver, String baseUsername) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Step 1: Enter the initial username
//        WebElement usernameInput = wait.until(ExpectedConditions.elementToBeClickable(usernameField));
        synchronization_methods.waitFor_Element_toBe_clickable(driver, usernameField);
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(baseUsername);
        System.out.println("Entered username: " + baseUsername);

        // Step 2️: Wait briefly to check if "Already exists" message appears
        try {
            wait.withTimeout(Duration.ofSeconds(2))
                    .until(ExpectedConditions.visibilityOfElementLocated(alreadyExistsMsg));

            // Step 3️: If message appears → generate a new username using Faker
            String uniqueUsername = baseUsername +"azcvn"+ faker.number().digits(3);
            System.out.println("Username already exists! Trying new username: " + uniqueUsername);

            driver.findElement(usernameField).clear();
            driver.findElement(usernameField).sendKeys(uniqueUsername);

            // Step 4️: Wait again to confirm the error is gone
            wait.until(ExpectedConditions.invisibilityOfElementLocated(alreadyExistsMsg));
            System.out.println("Username is now unique and accepted.");

        } catch (Exception e) {
            System.out.println("No duplicate username message appeared.");
        }
    }
    // Add a new user
    public void addNewUser(String empName, String username, String password) throws InterruptedException {
        synchronization_methods.waitFor_Element_toBe_clickable(driver, addButton);
        driver.findElement(addButton).click();

        // Wait for add user form to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(userRoleDropdown));
        selectFromDropdown(userRoleDropdown, "Admin");
        selectFromDropdown(statusDropdown, "Enabled");

        enterEmployeeName(empName);
        //_______________________________________________________
         // driver.findElement(usernameField).sendKeys(username);
        enterUniqueUsername(driver,username);
        //__________________________________________________________
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(confirmPasswordField).sendKeys(password);

        driver.findElement(saveButton).click();
    }
    // Search for a specific user
    public void searchUser(String username) {
        synchronization_methods.WaitForvisibilityOfElementLocated(driver, searchUsernameField);
        WebElement searchBox = driver.findElement(searchUsernameField);
        searchBox.clear();
        searchBox.sendKeys(username);
        driver.findElement(searchButton).click();
    }
    // Delete first visible user
    public void deleteUser() {
        synchronization_methods.WaitForvisibilityOfElementLocated(driver, deleteButton);
        driver.findElement(deleteButton).click();

        synchronization_methods.WaitForvisibilityOfElementLocated(driver, confirmDeleteButton);
        driver.findElement(confirmDeleteButton).click();
        synchronization_methods.WaitForvisibilityOfElementLocated(driver,recordCount);
    }
    // ------------------- Helper Methods -------------------
    // Select option from dropdown by visible text
    private void selectFromDropdown(By dropdownLocator, String optionText) {
        driver.findElement(dropdownLocator).click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownOptions));

        List<WebElement> options = driver.findElements(dropdownOptions);
        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(optionText)) {
                option.click();
                break;
            }
        }
    }

    // Handle employee name auto-suggestion
    private void enterEmployeeName(String empName) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Locate Employee Name input field
        WebElement empField = driver.findElement(employeeNameField);
        synchronization_methods.waitFor_Element_toBe_clickable(driver,employeeNameField);
        empField.click();
        empField.clear();
        empField.sendKeys(empName);
        Thread.sleep(2000);
        synchronization_methods.WaitForvisibilityOfElementLocated(driver,Listbox);

        System.out.println("Typed employee name: " + empName);
        // Wait for suggestion list to appear
        synchronization_methods.waitFor_Element_toBe_clickable(driver,Listbox);
        // Send keyboard keys: Arrow Down + Enter
        empField.sendKeys(Keys.ARROW_DOWN);
        wait.until(ExpectedConditions.attributeToBeNotEmpty(empField, "value")); // ensures a value got selected
        empField.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(Listbox));
        System.out.println("Selected first suggestion using keyboard.");

    }

    public void ResetSearch() {
        synchronization_methods.waitFor_Element_toBe_clickable(driver,ResetBottom);
        driver.findElement(ResetBottom).click();
        synchronization_methods.WaitForvisibilityOfElementLocated(driver,recordCount);
    }
}
