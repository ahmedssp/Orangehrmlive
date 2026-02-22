package utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import factory.DriverFactory;

import java.io.IOException;

public class ReporterUtil {

    /**
     * Logs step with screenshot to ExtentReports (thread-safe)
     *
     * @param status     pass/fail/info/warning (case-insensitive)
     * @param stepDetail description of the step
     */
    public static void reporter(String status, String stepDetail) throws InterruptedException {
        // Optional delay if really needed
        Thread.sleep(1000);

        String base64Screenshot;
        try {
            base64Screenshot = ScreenshotUtil.captureBase64(DriverFactory.getDriver());
        } catch (IOException e) {
            throw new RuntimeException("Failed to capture screenshot", e);
        }

        var logger = ExtentManager.getTest();

        switch (status.toLowerCase()) {
            case "pass":
                logger.pass(stepDetail, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
                break;

            case "fail":
                logger.fail(stepDetail, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
                break;

            case "info":
                logger.info(stepDetail);
                break;

            case "warning":
                logger.warning(stepDetail);
                break;

            default:
                logger.info(stepDetail);
                break;
        }
    }
}