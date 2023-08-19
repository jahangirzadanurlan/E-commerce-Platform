package com.example.productservice.service;

import com.example.productservice.model.entity.WholesaleProduct;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URISyntaxException;
import java.util.List;

public interface IWholesaleProductService {
    Page<WholesaleProduct> getAllProducts(Pageable pageable) throws JsonProcessingException, URISyntaxException;
    WholesaleProduct getProductByTitle(String title) throws URISyntaxException, JsonProcessingException;
}
