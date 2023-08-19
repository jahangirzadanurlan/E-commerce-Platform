package com.example.productservice.repository;

import com.example.productservice.model.entity.WholesaleProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WholesaleProductRepository extends JpaRepository<WholesaleProduct,Long> {
    Optional<WholesaleProduct> findWholesaleProductByTitle(String title);
    Page<WholesaleProduct> findAll(Pageable pageable);
}
