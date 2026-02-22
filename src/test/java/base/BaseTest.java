package base;

import Pages.P0_LoginPage;
import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import utils.ExtentManager;
import utils.ReporterUtil;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseTest {

    protected P0_LoginPage P0;
    protected WebDriver driver;

    @BeforeSuite
    public void beforeSuite() throws IOException {
        ExtentManager.init("Reports/extent-reports-config.json");
    }

    @BeforeClass
    public void setUpClass() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        P0 = new P0_LoginPage(driver);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        ExtentManager.createTest(method.getName());
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException, InterruptedException {

        if (result.getStatus() == ITestResult.FAILURE) {
            ReporterUtil.reporter("fail", "Test Failed: " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ReporterUtil.reporter("pass", "Test Passed: " + result.getName());
        }
    }

    @AfterClass
    public void tearDownClass() {
        DriverFactory.quitDriver();
    }

    @AfterSuite
    public void afterSuite() {
        ExtentManager.flushReport();
    }
}