package edu.aitu.oop3.db.entities;

import java.math.BigDecimal;
import java.util.Date;

public class OrderBuilder {
    private long id;
    private long customerId;
    private Date orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private String orderType;

    public OrderBuilder() {
    }

    public OrderBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public OrderBuilder setCustomerId(long customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderBuilder setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public OrderBuilder setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public OrderBuilder setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public OrderBuilder setOrderType(String orderType) {
        this.orderType = orderType;
        return this;
    }

    public Order build() {
        Order order = new Order(id, customerId, orderDate, totalAmount, status);
        order.setOrderType(orderType);
        return order;
    }
}
