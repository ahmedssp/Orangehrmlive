package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.IOException;

public class ScreenshotUtil {

    /**
     * Capture screenshot as base64 string
     */
    public static String captureBase64(WebDriver driver) throws IOException {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

}