package edu.aitu.oop3.db.components.ordering;

import edu.aitu.oop3.db.commonInfrastructure.database.IDB;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    private final IDB db;

    public OrderRepositoryImpl(IDB db) {
        this.db = db;
    }

    @Override
    public long createOrder(Order order) {
        String sql = "INSERT INTO orders (customer_id, order_date, total_amount, status, order_type) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, order.getCustomerId());
            stmt.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setBigDecimal(3, order.getTotalAmount());
            stmt.setString(4, order.getStatus().name());
            stmt.setString(5, order.getOrderType());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
        } catch (SQLException e) {
            System.out.println("Error creating order: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public void addOrderItem(OrderItem item) {
        String sql = "INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, item.getOrderId());
            stmt.setLong(2, item.getMenuItemId());
            stmt.setInt(3, item.getQuantity());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
    }

    @Override
    public List<Order> getActiveOrders() {
        String sql = "SELECT * FROM orders WHERE status = 'ACTIVE'";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setCustomerId(rs.getLong("customer_id"));
                order.setOrderDate(rs.getDate("order_date"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setOrderType(rs.getString("order_type"));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching orders: " + e.getMessage());
        }
        return orders;
    }

    @Override
    public Order getOrderById(long id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setCustomerId(rs.getLong("customer_id"));
                order.setOrderDate(rs.getDate("order_date"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setOrderType(rs.getString("order_type"));
                return order;
            }
        } catch (SQLException e) {
            System.out.println("Error getting order: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<OrderItem> getOrderItems(long orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        List<OrderItem> items = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(new OrderItem(
                        rs.getLong("id"),
                        rs.getLong("order_id"),
                        rs.getLong("menu_item_id"),
                        rs.getInt("quantity"),
                        null
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching items: " + e.getMessage());
        }
        return items;
    }

    @Override
    public void updateOrderTotal(long orderId, BigDecimal newTotal) {
        String sql = "UPDATE orders SET total_amount = ? WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, newTotal);
            stmt.setLong(2, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating total: " + e.getMessage());
        }
    }

    @Override
    public void updateStatus(long orderId, OrderStatus status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            stmt.setLong(2, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating status: " + e.getMessage());
        }
    }
}