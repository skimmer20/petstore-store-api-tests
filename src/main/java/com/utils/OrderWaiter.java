package com.utils;

import com.api.StoreController;
import com.dto.Order;
import com.dto.response.ErrorOrderResponse;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.testng.Assert.assertEquals;

public class OrderWaiter {

    private final StoreController store = new StoreController();

    /**
     * Wait until created order is available
     */
    public void waitForOrderAvailable(Long orderId, Order expectedOrder) {
        await()
                .atMost(20, TimeUnit.SECONDS)
                .pollInterval(1000, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> {
                    Order order = store.getOrderById(orderId, 200);
                    assertEquals(order.getId(), expectedOrder.getId(), "Order IDs must match");
                });
    }

    /**
     * Wait until the order is removed
     */
    public void waitForOrderDeletion(Long orderId) {
        await()
                .atMost(10, TimeUnit.SECONDS)
                .pollInterval(1000, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> {
                    ErrorOrderResponse error = store.getOrderByIdNegative(orderId, 404);
                    assertEquals(error.getMessage(), "Order not found", "Order is not deleted");
                });
    }
}
