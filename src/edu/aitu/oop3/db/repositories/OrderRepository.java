package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.entities.Order;
import edu.aitu.oop3.db.entities.OrderItem;
import edu.aitu.oop3.db.entities.OrderStatus;
import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository {

    long createOrder(Order order);

    void addOrderItem(OrderItem item);
    List<Order> getActiveOrders();
    Order getOrderById(long id);
    List<OrderItem> getOrderItems(long orderId);
    void updateOrderTotal(long orderId, BigDecimal newTotal);
    void updateStatus(long orderId, OrderStatus status);
}