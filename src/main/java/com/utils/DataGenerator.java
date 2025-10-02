package com.utils;

import com.dto.Order;
import com.enums.OrderStatus;
import java.time.Instant;

public class DataGenerator {
    public static Order validOrder() {
        Order order = new Order();
        order.setId(Instant.now().toEpochMilli()%1000000);
        order.setPetId(100L);
        order.setQuantity(1);
        order.setShipDate(Instant.now().toString());
        order.setStatus(OrderStatus.PLACED.value());
        order.setComplete(false);
        return order;
    }

    public static Order invalidOrderMissingPetId() {
        Order order = validOrder();
        order.setPetId(null);
        return order;
    }
}
