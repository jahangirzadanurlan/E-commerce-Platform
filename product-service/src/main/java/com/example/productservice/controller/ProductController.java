package com.example.productservice.controller;

import com.example.productservice.model.dto.request.ProductRequestDto;
import com.example.productservice.model.entity.MarketProduct;
import com.example.productservice.model.entity.WholesaleProduct;
import com.example.productservice.service.IMarketProductService;
import com.example.productservice.service.IWholesaleProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final IWholesaleProductService wholesaleProductService;
    private final IMarketProductService marketProductService;


    @GetMapping("/wholesale-products")//For example: http://localhost:8080/wholesale-products?page=1&size=10
    public Page<WholesaleProduct> getWholesaleProducts(Pageable pageable) throws JsonProcessingException, URISyntaxException {
        return wholesaleProductService.getAllProducts(pageable);
    }

    @GetMapping("/wholesale-product")
    public WholesaleProduct getWholesaleProductByTitle(@RequestParam(name = "title") String title) throws URISyntaxException, JsonProcessingException {
        return wholesaleProductService.getProductByTitle(title);
    }

    @GetMapping("/market-products")
    public List<MarketProduct> getAllMarketProducts(){
        return marketProductService.getAllProducts();
    }

    @PostMapping("/buy-product")
    public ResponseEntity<String> buyProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody ProductRequestDto productRequestDto) throws URISyntaxException, JsonProcessingException {
        return ResponseEntity.ok().body(marketProductService.saveProduct(authHeader,productRequestDto));
    }

    @DeleteMapping("/market-products/{id}")
    public ResponseEntity<String> deleteProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable Long id){
        marketProductService.deleteProduct(authHeader,id);
        return ResponseEntity.ok().body("Deleting is successfully");
    }




}

