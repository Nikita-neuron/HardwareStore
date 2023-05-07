package com.mirea.hardwarestore.service;

import com.mirea.hardwarestore.model.store.Basket;
import com.mirea.hardwarestore.model.store.Product;

import java.util.List;

public interface BasketService {
    Basket save(Basket basket);
    Basket findById(Long id);
    List<Basket> findAll();
    Basket findByUserId(Long id);
    void deleteById(Long id);

    Basket addProductByBasketId(Long id, Product product);
    Basket deleteProductById(Long basketId, Long productId);
    Basket deleteAllProducts(Long id);
}
