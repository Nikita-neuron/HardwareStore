package com.mirea.hardwarestore.model.store;

import com.mirea.hardwarestore.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "baskets")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "basket_product",
        joinColumns = @JoinColumn(name = "basket_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

}
