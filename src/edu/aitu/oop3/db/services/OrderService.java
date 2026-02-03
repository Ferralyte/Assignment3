package edu.aitu.oop3.db.services;

import edu.aitu.oop3.db.entities.*;
import edu.aitu.oop3.db.entities.orderType.*;
import edu.aitu.oop3.db.exceptions.*;
import edu.aitu.oop3.db.repositories.OrderRepository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderService {
    private final OrderRepository orderRepo;
    private final MenuService menuService;
    private final PaymentService paymentService;

    public OrderService(OrderRepository orderRepo, MenuService menuService, PaymentService paymentService) {
        this.orderRepo = orderRepo;
        this.menuService = menuService;
        this.paymentService = paymentService;
    }

    public long placeOrder(long customerId, List<OrderItem> items, OrderType orderType) throws Exception {
        System.out.println("[SERVICE] Placing order for customer " + customerId);

        for (OrderItem item : items) {
            MenuItem menu = menuService.getMenuItem(item.getMenuItemId());
            if (menu == null) {
                throw new MenuItemNotAvailableException("Menu item " + item.getMenuItemId() + " not found.");
            }
            if (!menu.isAvailable()) {
                throw new MenuItemNotAvailableException(menu.getName() + " is currently unavailable.");
            }
            if (item.getQuantity() <= 0) {
                throw new InvalidQuantityException("Quantity must be positive.");
            }
        }

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderDate(new Date());
        order.setTotalAmount(BigDecimal.ZERO);
        order.setStatus(OrderStatus.ACTIVE);

        order.setOrderType(orderType.getType());

        long orderId = orderRepo.createOrder(order);

        for (OrderItem item : items) {
            item.setOrderId(orderId);
            orderRepo.addOrderItem(item);
        }

        BigDecimal total = calculateOrderTotal(orderId);
        orderRepo.updateOrderTotal(orderId, total);

        return orderId;
    }

    public List<Order> getActiveOrders() {
        return orderRepo.getActiveOrders();
    }

    public BigDecimal calculateOrderTotal(long orderId) {
        List<OrderItem> items = orderRepo.getOrderItems(orderId);
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : items) {
            MenuItem menu = menuService.getMenuItem(item.getMenuItemId());
            BigDecimal price = menu.getPrice();
            total = total.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }

    public BigDecimal calculateOrderTotalWithTax(long orderId) throws OrderNotFoundException {
        Order order = orderRepo.getOrderById(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order " + orderId + " not found");
        }

        BigDecimal baseTotal = calculateOrderTotal(orderId);
        return paymentService.calculateTotalWithTax(baseTotal);
    }

    public void markCompleted(long orderId) throws OrderNotFoundException {
        Order order = orderRepo.getOrderById(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order not found: " + orderId);
        }
        orderRepo.updateStatus(orderId, OrderStatus.COMPLETED);
    }
}