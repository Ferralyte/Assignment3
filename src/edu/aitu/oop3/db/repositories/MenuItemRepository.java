package edu.aitu.oop3.db.repositories;
import edu.aitu.oop3.db.entities.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository {
    List<MenuItem> findAll();
    Optional<MenuItem> findById(long id);
}
