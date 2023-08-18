package com.example.productservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "product",url = "https://dummyjson.com")
public interface ProductServiceClient {

    @GetMapping("/products")
    String getProducts();
}
