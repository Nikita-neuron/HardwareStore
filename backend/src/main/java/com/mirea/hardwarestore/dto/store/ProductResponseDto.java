package com.mirea.hardwarestore.dto.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mirea.hardwarestore.model.store.Category;
import com.mirea.hardwarestore.model.store.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponseDto {
    private Long id;
    private String title;
    private int price;
    private List<CategoryProductDto> categories;

    public static ProductResponseDto fromProduct(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setPrice(product.getPrice());

        List<Category> categories = product.getCategories();
        List<CategoryProductDto> categoryProductDtoList = new ArrayList<>();
        categories.forEach(category -> categoryProductDtoList.add(CategoryProductDto.fromCategory(category)));

        productResponseDto.setCategories(categoryProductDtoList);

        return productResponseDto;
    }
}
