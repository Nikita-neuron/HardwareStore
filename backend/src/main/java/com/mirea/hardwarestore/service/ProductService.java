package com.mirea.hardwarestore.service;

import com.mirea.hardwarestore.model.store.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);
    Product findById(Long id);
    List<Product> getProducts();
    void deleteById(Long id);
}
