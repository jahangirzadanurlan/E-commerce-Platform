package com.example.productservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarketProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    @Builder.Default
    boolean status = true;
    boolean sellStatus;
    String description;
    double price;
    double rating;
    int stock;
    String brand;
    String category;
    String thumbnail;

    @ManyToOne
    @JsonIgnore
    User user;



}
