package utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.IOException;

public class ExtentManager {

    private static ExtentReports extent;                 // Single ExtentReports instance
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();  // Thread-safe per-test logging

    /**
     * Initialize ExtentReports once per suite
     *
     * @param jsonConfigPath path to extent-reports-config.json
     */
    public static void init(String jsonConfigPath) throws IOException {
        if (extent == null) {
            // Create Spark reporter
            ExtentSparkReporter spark = new ExtentSparkReporter("Reports/extent-report.html");

            // Load JSON config if exists
            File configFile = new File(jsonConfigPath);
            if (configFile.exists()) {
                spark.loadJSONConfig(configFile);
            }

            // Create ExtentReports instance and attach reporter
            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Suite-level logger
            ExtentTest suiteLogger = extent.createTest("Test Suite is started");
            suiteLogger.info("Automation suite execution has started");
        }
    }

    /**
     * Create a new test logger for a test method
     *
     * @param testName name of the test method
     */
    public static void createTest(String testName) {
        test.set(extent.createTest(testName));
    }

    /**
     * Get the ExtentTest for the current thread
     *
     * @return ExtentTest instance
     */
    public static ExtentTest getTest() {
        return test.get();
    }

    /**
     * Get the ExtentReports instance
     *
     * @return ExtentReports
     */
    public static ExtentReports getExtent() {
        return extent;
    }

    /**
     * Flush the report at the end of suite
     */
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}