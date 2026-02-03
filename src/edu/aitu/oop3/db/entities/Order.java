package edu.aitu.oop3.db.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
    private long id;
    private long customerId;
    private Date orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItem> items;
    private String orderType;

    public Order() {}

    public Order(long id, long customerId, Date orderDate, BigDecimal totalAmount, OrderStatus status) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getCustomerId() { return customerId; }
    public void setCustomerId(long customerId) { this.customerId = customerId; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public String getOrderType() { return orderType; }
    public void setOrderType(String orderType) { this.orderType = orderType; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", type='" + orderType + '\'' +
                '}';
    }
}