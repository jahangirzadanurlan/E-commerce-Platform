package com.example.productservice.repository;

import com.example.productservice.model.entity.WholesaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WholesaleProductRepository extends JpaRepository<WholesaleProduct,Long> {
    Optional<WholesaleProduct> findWholesaleProductByTitle(String title);
}
