package com.mirea.hardwarestore.controller.store;

import com.mirea.hardwarestore.dto.store.ProductResponseDto;
import com.mirea.hardwarestore.dto.store.ProductSaveDto;
import com.mirea.hardwarestore.model.store.Category;
import com.mirea.hardwarestore.model.store.Product;
import com.mirea.hardwarestore.service.CategoryService;
import com.mirea.hardwarestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/products/")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "id/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable(name = "id") Long id) {
        Product product = productService.findById(id);
        if (product == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        ProductResponseDto productResponseDto = ProductResponseDto.fromProduct(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProductSaveDto productSaveDto) {
        Product product = productSaveDto.toProduct();

        List<String> categories = productSaveDto.getCategories();
        List<Category> categoryList = new ArrayList<>();
        categories.forEach(title -> categoryList.add(categoryService.findCategoryByTitle(title)));
        product.setCategories(categoryList);

        product = productService.save(product);

        if (product == null) return new ResponseEntity<>("Product with title: " + productSaveDto.getTitle() + " already exist", HttpStatus.BAD_REQUEST);

        ProductResponseDto productResponseDto = ProductResponseDto.fromProduct(product);

        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<Product> products = productService.getProducts();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        products.forEach(product -> productResponseDtoList.add(ProductResponseDto.fromProduct(product)));

        return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);
    }

    @DeleteMapping(value = "id/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) {
        productService.deleteById(id);
    }
}
