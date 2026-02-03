package edu.aitu.oop3.db.services;

import edu.aitu.oop3.db.entities.MenuItem;
import edu.aitu.oop3.db.repositories.MenuItemRepository;
import java.util.List;

public class MenuService {
    private final MenuItemRepository menuRepo;

    public MenuService(MenuItemRepository menuRepo) {
        this.menuRepo = menuRepo;
    }

    public List<MenuItem> getAllMenu() {
        return menuRepo.getAllMenuItems();
    }

    public MenuItem getMenuItem(long id) {
        return menuRepo.getMenuItemById(id);
    }
}