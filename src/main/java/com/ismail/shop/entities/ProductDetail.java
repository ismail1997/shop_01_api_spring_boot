package com.ismail.shop.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String value;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}