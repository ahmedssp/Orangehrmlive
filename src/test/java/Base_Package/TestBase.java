package Base_Package;

import Pages.P0_LoginPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static Handler.HelperClass.capture;

public class TestBase {
    protected P0_LoginPage P0;
    public static boolean executeBeforeMethod = true;
    protected static ExtentReports extent;
    protected static ExtentSparkReporter reporter;
    public static ExtentTest logger;
    protected static WebDriver d;

    @BeforeSuite
    public void setUpSuite() throws IOException {
        extent = new ExtentReports();
        logger = extent.createTest("Test Suite is started");
        ExtentSparkReporter spark = new ExtentSparkReporter("Reports/extent-report.html");
        spark.loadJSONConfig(new File("Reports/extent-reports-config.json"));
        extent.attachReporter(spark);
    }

    @BeforeMethod
    public void setUp() {
        String baseurl="https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
        reporter = new ExtentSparkReporter("Reports/extent-report.html");
        extent.attachReporter(reporter);
        d = new ChromeDriver();
        d.manage().window().maximize();
        P0 =new P0_LoginPage(d);

        if (executeBeforeMethod) {
            d.get(baseurl);
            d.navigate().to(baseurl);
            logger.info("login page loaded successfully");
        }
    }

    @AfterMethod
    public void end(ITestResult result) throws InterruptedException {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot camera = (TakesScreenshot) d;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            String directoryPath = "Reports/failed tests screenshot/";

            // Create directory if it does not exist
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                boolean dirCreated = directory.mkdirs();
                if (!dirCreated) {
                    System.out.println("Failed to create directory for screenshots.");
                    // You can choose to throw an exception or handle the failure in another way
                    return;
                }
            }

            try {
                Files.move(screenshot.toPath(), new File(directoryPath + result.getName() + ".png").toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        d.quit();
        logger.info("Test is complete successfully");
    }

    @AfterSuite
    public void tearDownSuite() throws IOException {
        extent.flush();
    }

    public static void reporter(String status, String stepDetail) throws InterruptedException {
        Thread.sleep(1000);
        String base64Screenshot;
        try {
            base64Screenshot = capture(d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (status.equalsIgnoreCase("pass")) {
            logger.pass(stepDetail, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } else if (status.equalsIgnoreCase("fail")) {
            logger.fail(stepDetail, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } else if (status.equalsIgnoreCase("info")) {
            logger.info(stepDetail);
        } else if (status.equalsIgnoreCase("Warning")) {
            logger.warning(stepDetail);
        }
    }


}
