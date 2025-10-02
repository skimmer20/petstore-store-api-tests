package com.api;

import com.dto.Order;
import com.dto.response.ErrorOrderResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class StoreController {
    private static final String BASE_PATH = "/store";

    //we could also use Response for CRUD operations
    public Order placeOrder(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(BASE_PATH + "/order")
                .then().log().all()
                .statusCode(200)
                .extract().as(Order.class);
    }

    public Order getOrderById(long orderId, int expectedStatus) {
        return given()
                .when()
                .log().all()
                .get(BASE_PATH + "/order/" + orderId)
                .then()
                .log().all()
                .statusCode(expectedStatus)
                .extract()
                .as(Order.class);
    }

    public ErrorOrderResponse getOrderByIdNegative(long orderId, int expectedStatus) {
        return given()
                .when()
                .get(BASE_PATH + "/order/" + orderId)
                .then()
                .statusCode(expectedStatus)
                .log().all()
                .extract()
                .as(ErrorOrderResponse.class);
    }

    public Response deleteOrder(long id) {
        return given()
                .when()
                .delete(BASE_PATH + "/order/" + id)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Map<String, Integer> getInventory() {
        return given()
                .when()
                .get(BASE_PATH + "/inventory")
                .then()
                .log().all()
                .extract()
                .as(new TypeRef<Map<String, Integer>>() {});

    }
}
