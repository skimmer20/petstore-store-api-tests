package com.base;

import com.api.ApiBase;
import com.configuration.ConfigReader;
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
