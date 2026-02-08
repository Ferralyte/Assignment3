package edu.aitu.oop3.db.components.delivery;

public class PickupOrder implements OrderType {
    public String getType() {
        return "PICKUP";
    }
}