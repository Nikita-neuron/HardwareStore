package com.mirea.hardwarestore.service.impl;

import com.mirea.hardwarestore.model.store.Basket;
import com.mirea.hardwarestore.model.store.Product;
import com.mirea.hardwarestore.repository.BasketRepository;
import com.mirea.hardwarestore.service.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    @Autowired
    private BasketRepository basketRepository;

    @Override
    public Basket save(Basket basket) {
        return basketRepository.save(basket);
    }

    @Override
    public Basket addProductByBasketId(Long id, Product product) {
        Basket basket = basketRepository.findById(id).orElse(null);
        if (basket == null) return null;

        List<Product> products = basket.getProducts();
        products.add(product);
        basket.setProducts(products);
        basket = basketRepository.save(basket);

        return basket;
    }

    @Override
    public Basket deleteProductById(Long basketId, Long productId) {
        Basket basket = basketRepository.findById(basketId).orElse(null);
        if (basket == null) return null;

        List<Product> products = basket.getProducts();
        List<Product> productList = new ArrayList<>();
        products.forEach(product -> {
            if (product.getId() != productId) productList.add(product);
        });
        basket.setProducts(productList);
        basket = basketRepository.save(basket);
        return basket;
    }

    @Override
    public Basket deleteAllProducts(Long id) {
        Basket basket = basketRepository.findById(id).orElse(null);
        if (basket == null) return null;

        basket.setProducts(new ArrayList<>());
        basket = basketRepository.save(basket);

        return basket;
    }

    @Override
    public Basket findByUserId(Long id) {
        return basketRepository.findByUserId(id);
    }

    @Override
    public void deleteById(Long id) {
        basketRepository.deleteById(id);
    }

    @Override
    public Basket findById(Long id) {
        return basketRepository.findById(id).orElse(null);
    }

    @Override
    public List<Basket> findAll() {
        return basketRepository.findAll();
    }
}
