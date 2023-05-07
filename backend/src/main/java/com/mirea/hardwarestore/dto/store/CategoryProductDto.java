package com.mirea.hardwarestore.dto.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mirea.hardwarestore.model.store.Category;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryProductDto {
    private Long id;
    private String title;

    public static CategoryProductDto fromCategory(Category category) {
        CategoryProductDto categoryProductDto = new CategoryProductDto();
        categoryProductDto.setId(category.getId());
        categoryProductDto.setTitle(category.getTitle());
        return categoryProductDto;
    }
}
