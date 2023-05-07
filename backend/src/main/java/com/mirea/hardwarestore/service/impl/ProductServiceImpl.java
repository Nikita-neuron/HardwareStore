package com.mirea.hardwarestore.service.impl;

import com.mirea.hardwarestore.model.store.Product;
import com.mirea.hardwarestore.repository.ProductRepository;
import com.mirea.hardwarestore.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        Product product1 = productRepository.findByTitle(product.getTitle());
        if (product1 != null) return null;
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
