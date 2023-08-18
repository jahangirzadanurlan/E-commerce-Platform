package com.example.productservice.service;

import com.example.productservice.model.entity.WholesaleProduct;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.URISyntaxException;
import java.util.List;

public interface IWholesaleProductService {
    List<WholesaleProduct> getAllProducts() throws JsonProcessingException, URISyntaxException;
    WholesaleProduct getProductByTitle(String title) throws URISyntaxException, JsonProcessingException;
}
