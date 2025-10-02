package com.tests;

import com.api.StoreController;
import com.base.BaseTest;
import com.dto.Order;
import com.dto.response.ErrorOrderResponse;
import com.utils.DataGenerator;
import com.utils.ExtentTestListener;
import com.utils.InventoryWaiter;
import com.utils.OrderWaiter;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Map;


@Listeners(ExtentTestListener.class)
public class StoreTests extends BaseTest {

    private final StoreController store = new StoreController();
    private final OrderWaiter orderWaiter = new OrderWaiter();
    private final InventoryWaiter inventoryWaiter = new InventoryWaiter();

    private Order order;
    private Order createdOrder;
    private static boolean orderExists = false;

    @BeforeTest
    public void setup() {
        if (!orderExists) {
            order = DataGenerator.validOrder();
            createdOrder = store.placeOrder(order);
            orderExists = true;
        }
    }

    @Test(description = "Verify order is submitted successfully")
    public void verifyOrderIsSubmitted() {
        Assert.assertNotNull(createdOrder.getId(), "Order ID should not be null");
        Assert.assertEquals(createdOrder.getPetId(), order.getPetId(), "Pet ID should match");
        Assert.assertEquals(createdOrder.getStatus(), "placed", "Status should be 'placed'");
    }

    @Test(description = "Get order by id")
    public void getOrderById() {
        orderWaiter.waitForOrderAvailable(createdOrder.getId(), createdOrder);
        Assert.assertNotNull(order, "Fetched order should not be null");
        Assert.assertEquals(order.getId(), createdOrder.getId(), "Order ID should match");
        Assert.assertEquals(order.getPetId(), createdOrder.getPetId(), "Pet ID should match");
        Assert.assertEquals(order.getStatus(), createdOrder.getStatus(), "Status should match");
    }

    @Test(description = "Verify non existing order")
    public void getOrderByIdNotFound() {
        ErrorOrderResponse errorOrderResponse = store.getOrderByIdNegative(9999, 404);
        Assert.assertNotNull(errorOrderResponse, "Error response should not be null");
        Assert.assertEquals(errorOrderResponse.getMessage(), "Order not found", "Error message should be 'Order not found'");
    }

    @Test(description = "Delete order")
    public void deleteOrder() {
        store.deleteOrder(createdOrder.getId());
        orderExists = false;
        orderWaiter.waitForOrderDeletion(createdOrder.getId());
        ErrorOrderResponse errorOrderResponse = store.getOrderByIdNegative(createdOrder.getId(), 404);
        Assert.assertEquals(errorOrderResponse.getMessage(), "Order not found", "Order should be deleted");
    }

    @Test(description = "Delete non existing order")
    public void deleteNonExistingOrder() {
        ErrorOrderResponse errorOrderResponse = store.getOrderByIdNegative(9999, 404);
        Assert.assertEquals(errorOrderResponse.getMessage(), "Order not found", "Expected 'Order not found' for non-existent order");
    }

    @Test(description = "Get inventory")
    public void getInventoryPositive() {
        Map<String, Integer> result = inventoryWaiter.waitForField("2cjl7xv5", 10);
        Assert.assertNotNull(result, "Inventory result should not be null");
        Assert.assertTrue(result.containsKey("available"), "Inventory should contain 'available'");
        Assert.assertTrue(result.get("available") >= 10, "Available inventory should be at least 10");
    }
}
