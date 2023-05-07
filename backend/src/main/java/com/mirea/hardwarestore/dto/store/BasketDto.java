package com.mirea.hardwarestore.dto.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mirea.hardwarestore.model.store.Basket;
import com.mirea.hardwarestore.model.store.Product;
import com.mirea.hardwarestore.model.user.User;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasketDto {
    private Long userId;
    private List<Product> products;

    public Basket toBasket() {
        Basket basket = new Basket();
        basket.setProducts(products);

        return basket;
    }

    public static BasketDto fromBasket(Basket basket) {
        BasketDto basketDto = new BasketDto();
        basketDto.setUserId(basket.getUser().getId());
        basketDto.setProducts(basket.getProducts());

        return basketDto;
    }
}
