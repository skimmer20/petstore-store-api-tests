package com.api;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.apache.http.params.CoreConnectionPNames;

public class ApiBase {
    public static void init(String baseUri, int connectTimeout, int socketTimeout) {
        RestAssured.baseURI = baseUri;
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                    .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, connectTimeout)
                    .setParam(CoreConnectionPNames.SO_TIMEOUT, socketTimeout));
    }
}
