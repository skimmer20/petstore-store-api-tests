package com.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.configuration.annotations.Flaky;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter reporter = new ExtentSparkReporter("target/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (isFlaky(result)) {
            test.get().log(Status.WARNING, "Test passed, but marked as @Flaky: " + getFlakyReason(result));
            test.get().assignCategory("FLAKY");
        } else {
            test.get().pass("Test passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (isFlaky(result)) {
            test.get().log(Status.WARNING, "Test failed and marked as @Flaky: " + getFlakyReason(result));
            test.get().assignCategory("FLAKY");
        } else {
            test.get().fail("Test failed: " + result.getThrowable());
        }
    }

    private boolean isFlaky(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(Flaky.class);
    }

    private String getFlakyReason(ITestResult result) {
        Flaky flaky = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Flaky.class);
        return flaky != null ? flaky.reason() : "";
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test skipped");
    }
}
