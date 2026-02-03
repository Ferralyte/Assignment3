package edu.aitu.oop3.db.entities.orderType;

public class PickupOrder implements OrderType {
    public String getType() {
        return "PICKUP";
    }
}