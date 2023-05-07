package com.mirea.hardwarestore.controller.store;

import com.mirea.hardwarestore.dto.store.BasketDto;
import com.mirea.hardwarestore.dto.store.BasketResponseDto;
import com.mirea.hardwarestore.model.store.Basket;
import com.mirea.hardwarestore.model.store.Product;
import com.mirea.hardwarestore.model.user.User;
import com.mirea.hardwarestore.service.BasketService;
import com.mirea.hardwarestore.service.ProductService;
import com.mirea.hardwarestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/baskets/")
public class BasketController {
    @Autowired
    private BasketService basketService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<BasketResponseDto>> getAll() {
        List<Basket> baskets = basketService.findAll();
        List<BasketResponseDto> result = new ArrayList<>();
        baskets.forEach(basket -> result.add(BasketResponseDto.fromBasket(basket)));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "id/{id}")
    public ResponseEntity<BasketResponseDto> getById(@PathVariable(name = "id") Long id) {
        Basket basket = basketService.findById(id);

        if (basket == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        BasketResponseDto basketDto = BasketResponseDto.fromBasket(basket);

        return new ResponseEntity<>(basketDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BasketResponseDto> save(@RequestBody BasketDto basketDto) {
        Basket basket = basketDto.toBasket();
        User user = userService.findById(basketDto.getUserId());
        if (user == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        basket.setUser(user);
        basket = basketService.save(basket);
        return new ResponseEntity<>(BasketResponseDto.fromBasket(basket), HttpStatus.OK);
    }

    @GetMapping(value = "user/{id}")
    public ResponseEntity<BasketResponseDto> findByUserId(@PathVariable(name = "id") Long id) {
        Basket basket = basketService.findByUserId(id);
        if (basket == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        BasketResponseDto basketDto = BasketResponseDto.fromBasket(basket);

        return new ResponseEntity<>(basketDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "del/id/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) {
        basketService.deleteById(id);
    }

    @GetMapping(value = "id/{basketId}/addProduct/{productId}")
    public ResponseEntity<BasketResponseDto> addProductByBasketId(@PathVariable(name = "basketId") Long basketId,
                                                        @PathVariable(name = "productId") Long productId) {
        Product product = productService.findById(productId);
        if (product == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Basket basket = basketService.addProductByBasketId(basketId, product);
        BasketResponseDto basketDto = BasketResponseDto.fromBasket(basket);

        return new ResponseEntity<>(basketDto, HttpStatus.OK);
    }

    @GetMapping(value = "id/{basketId}/deleteProduct/{productId}")
    public ResponseEntity<BasketResponseDto> deleteProductById(@PathVariable(name = "basketId") Long basketId,
                                                    @PathVariable(name = "productId") Long productId) {
        Basket basket = basketService.deleteProductById(basketId, productId);

        BasketResponseDto basketDto = BasketResponseDto.fromBasket(basket);

        return new ResponseEntity<>(basketDto, HttpStatus.OK);
    }

    @GetMapping(value = "id/{basketId}/deleteAllProducts")
    public ResponseEntity<BasketResponseDto> deleteAllProducts(@PathVariable(name = "basketId") Long basketId) {
        Basket basket = basketService.deleteAllProducts(basketId);
        BasketResponseDto basketDto = BasketResponseDto.fromBasket(basket);

        return new ResponseEntity<>(basketDto, HttpStatus.OK);
    }
}
