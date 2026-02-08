package edu.aitu.oop3.db.components.menu;

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