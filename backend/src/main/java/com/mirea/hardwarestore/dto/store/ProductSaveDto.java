package com.mirea.hardwarestore.dto.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mirea.hardwarestore.model.store.Category;
import com.mirea.hardwarestore.model.store.Product;
import com.mirea.hardwarestore.service.CategoryService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductSaveDto {
    private String title;
    private int price;
    private List<String> categories;

    public Product toProduct() {
        Product product = new Product();
        product.setTitle(title);
        product.setPrice(price);

        return product;
    }
}
