package com.utils;

import com.api.StoreController;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import static org.awaitility.Awaitility.await;

public class InventoryWaiter {

    private final StoreController controller = new StoreController();

    /**
     * Wait until response contains the specific field
     */
    public Map<String, Integer> waitForField(String fieldName, int timeoutSeconds) {
        await()
                .atMost(timeoutSeconds, TimeUnit.SECONDS)
                .pollInterval(500, TimeUnit.MILLISECONDS)
                .until(() -> {
                    Map<String, Integer> inventory = controller.getInventory();
                    return inventory.containsKey(fieldName);
                });

        //return the latest JSON version after success verification
        return controller.getInventory();
    }
}
