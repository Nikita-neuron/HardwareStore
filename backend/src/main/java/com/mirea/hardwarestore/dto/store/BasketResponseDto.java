package com.mirea.hardwarestore.dto.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mirea.hardwarestore.model.store.Basket;
import com.mirea.hardwarestore.model.store.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasketResponseDto {
    private Long id;
    private Long userId;
    private List<ProductResponseDto> products;

    public static BasketResponseDto fromBasket(Basket basket) {
        BasketResponseDto basketResponseDto = new BasketResponseDto();
        basketResponseDto.setUserId(basket.getUser().getId());

        List<Product> products = basket.getProducts();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        products.forEach(product -> productResponseDtoList.add(ProductResponseDto.fromProduct(product)));

        basketResponseDto.setProducts(productResponseDtoList);
        basketResponseDto.setId(basket.getId());

        return basketResponseDto;
    }
}
