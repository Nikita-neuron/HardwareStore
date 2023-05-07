package com.mirea.hardwarestore.service;

import com.mirea.hardwarestore.model.store.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);
    Category findById(Long id);
    Category findCategoryByTitle(String title);
    List<Category> findAll();
    void deleteById(Long id);
}
