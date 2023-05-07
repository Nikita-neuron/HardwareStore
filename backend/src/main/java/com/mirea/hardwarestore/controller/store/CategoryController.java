package com.mirea.hardwarestore.controller.store;

import com.mirea.hardwarestore.dto.store.CategoryProductDto;
import com.mirea.hardwarestore.model.store.Category;
import com.mirea.hardwarestore.service.CategoryAlreadyExist;
import com.mirea.hardwarestore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/categories/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryProductDto>> getCategories() {
        List<Category> categories = categoryService.findAll();
        List<CategoryProductDto> categoryProductDtoList = new ArrayList<>();

        categories.forEach(category -> categoryProductDtoList.add(CategoryProductDto.fromCategory(category)));

        return new ResponseEntity<>(categoryProductDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "id/{id}")
    public ResponseEntity<CategoryProductDto> getById(@PathVariable(name = "id") Long id) {
        Category category = categoryService.findById(id);
        if (category == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        CategoryProductDto categoryProductDto = CategoryProductDto.fromCategory(category);
        return new ResponseEntity<>(categoryProductDto, HttpStatus.OK);
    }

    @GetMapping(value = "title/{title}")
    public ResponseEntity<CategoryProductDto> getByTitle(@PathVariable(name = "title") String title) {
        Category category = categoryService.findCategoryByTitle(title);
        if (category == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        CategoryProductDto categoryProductDto = CategoryProductDto.fromCategory(category);
        return new ResponseEntity<>(categoryProductDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Category category) {
        Category result = categoryService.save(category);
        if (result == null) return new ResponseEntity<>("Category with title: " + category.getTitle() + " already exist", HttpStatus.BAD_REQUEST);

        CategoryProductDto categoryProductDto = CategoryProductDto.fromCategory(result);
        return new ResponseEntity<>(categoryProductDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "id/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) {
        categoryService.deleteById(id);
    }
}
