package com.example.productservice.repository;

import com.example.productservice.model.entity.MarketProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarketProductRepository extends JpaRepository<MarketProduct,Long> {
    Optional<MarketProduct> findMarketProductByTitle(String title);
    List<MarketProduct> findAllByStatusIsTrue();

}
