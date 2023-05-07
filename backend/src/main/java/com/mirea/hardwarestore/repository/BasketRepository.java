package com.mirea.hardwarestore.repository;

import com.mirea.hardwarestore.model.store.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    Basket findByUserId(Long id);
}
