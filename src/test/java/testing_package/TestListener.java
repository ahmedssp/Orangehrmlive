package testing_package;

import base.BaseTest;
import utils.ReporterUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite is starting: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Executed after <test> tag of xml file
        System.out.println("Test Suite is ending: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Executed before each @Test method
        System.out.println("Starting test: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            ReporterUtil.reporter("pass", "Test passed successfully: " + result.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            ReporterUtil.reporter("fail", "Test failed: " + result.getName() +
                    "\nReason: " + result.getThrowable());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        try {
            ReporterUtil.reporter("info", "Test skipped: " + result.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not implemented
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }
}