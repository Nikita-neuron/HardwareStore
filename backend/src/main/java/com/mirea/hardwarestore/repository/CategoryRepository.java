package com.mirea.hardwarestore.repository;

import com.mirea.hardwarestore.model.store.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByTitle(String title);
}
