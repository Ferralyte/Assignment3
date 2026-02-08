package edu.aitu.oop3.db.components.menu;

import edu.aitu.oop3.db.commonInfrastructure.repository.Repository;

import java.util.List;

public interface MenuItemRepository extends Repository<MenuItem> {
    List<MenuItem> getAllMenuItems();
    MenuItem getMenuItemById(long id);
}