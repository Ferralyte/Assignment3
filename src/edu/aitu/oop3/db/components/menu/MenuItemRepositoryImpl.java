package edu.aitu.oop3.db.components.menu;

import edu.aitu.oop3.db.commonInfrastructure.database.IDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuItemRepositoryImpl implements MenuItemRepository {
    private final IDB db;

    public MenuItemRepositoryImpl(IDB db) {
        this.db = db;
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        String sql = "SELECT * FROM menu_items";
        List<MenuItem> items = new ArrayList<>();
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                items.add(new MenuItem(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getBoolean("available")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching menu: " + e.getMessage());
        }
        return items;
    }

    @Override
    public MenuItem getMenuItemById(long id) {
        String sql = "SELECT * FROM menu_items WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new MenuItem(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getBoolean("available")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching item by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<MenuItem> findAll() {
        return getAllMenuItems();
    }

    @Override
    public Optional<MenuItem> findById(long id) {
        return Optional.ofNullable(getMenuItemById(id));
    }

    @Override
    public MenuItem save(MenuItem entity) {
        return entity;
    }
}