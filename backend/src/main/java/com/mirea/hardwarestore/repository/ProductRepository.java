package com.mirea.hardwarestore.repository;

import com.mirea.hardwarestore.model.store.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product, Long> {
    Product findByTitle(String title);
}
