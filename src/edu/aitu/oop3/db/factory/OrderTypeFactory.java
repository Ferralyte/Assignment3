package edu.aitu.oop3.db.factory;

import edu.aitu.oop3.db.entities.orderType.*;

public class OrderTypeFactory {

    public static OrderType create(String type) {
        return switch (type.toUpperCase()) {
            case "DELIVERY" -> new DeliveryOrder();
            case "DINEIN" -> new DineInOrder();
            case "PICKUP"   -> new PickupOrder();
            default -> throw new IllegalArgumentException("Unknown order type: " + type);
        };
    }
}
