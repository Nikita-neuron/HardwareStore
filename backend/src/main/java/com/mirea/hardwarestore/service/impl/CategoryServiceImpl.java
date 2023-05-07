package com.mirea.hardwarestore.service.impl;

import com.mirea.hardwarestore.model.store.Category;
import com.mirea.hardwarestore.repository.CategoryRepository;
import com.mirea.hardwarestore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        if (categoryRepository.findCategoryByTitle(category.getTitle()) != null) {
            log.warn("IN save - category with title: {} already exist", category.getTitle());
            return null;
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category findCategoryByTitle(String title) {
        return categoryRepository.findCategoryByTitle(title);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
