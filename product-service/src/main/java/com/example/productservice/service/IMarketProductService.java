package com.example.productservice.service;

import com.example.productservice.model.dto.request.ProductRequestDto;
import com.example.productservice.model.entity.MarketProduct;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URISyntaxException;
import java.util.List;

public interface IMarketProductService {
    List<MarketProduct> getAllProducts();
    MarketProduct getProductByTitle(String title);
    String saveProduct(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token, ProductRequestDto productRequestDto) throws URISyntaxException, JsonProcessingException;
    void deleteProduct(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token, Long id);
    
}
