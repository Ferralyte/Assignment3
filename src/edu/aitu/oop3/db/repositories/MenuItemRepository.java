package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.entities.MenuItem;
import java.util.List;

public interface MenuItemRepository extends Repository<MenuItem> {
    List<MenuItem> getAllMenuItems();
    MenuItem getMenuItemById(long id);
}