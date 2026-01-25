package edu.aitu.oop3.db.services;

import edu.aitu.oop3.db.entities.MenuItem;
import edu.aitu.oop3.db.exceptions.MenuItemNotAvailableException;
import edu.aitu.oop3.db.repositories.MenuItemRepository;

import java.util.List;

public class MenuService {
    private final MenuItemRepository menuRepo;

    public MenuService(MenuItemRepository menuRepo) {
        this.menuRepo = menuRepo;
    }

    public List<MenuItem> getAllMenu() {
        return menuRepo.findAll();
    }

    public MenuItem getAvailableMenuItemOrThrow(long id) {
        MenuItem item = menuRepo.findById(id)
                .orElseThrow(() -> new MenuItemNotAvailableException("Menu item not found: " + id));

        if (!item.isAvailable()) {
            throw new MenuItemNotAvailableException("Menu item is not available: " + item.getName() + " (id=" + id + ")");
        }
        return item;
    }
}