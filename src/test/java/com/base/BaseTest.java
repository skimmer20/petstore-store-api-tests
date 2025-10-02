package com.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.api.ApiBase;
import com.utils.ConfigReader;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite
    public void setUpSuite() {
        String baseUri = ConfigReader.get("base.uri");
        int connect = Integer.parseInt(ConfigReader.get("connect.timeout"));
        int read = Integer.parseInt(ConfigReader.get("read.timeout"));
        ApiBase.init(baseUri, connect, read);
    }
}
