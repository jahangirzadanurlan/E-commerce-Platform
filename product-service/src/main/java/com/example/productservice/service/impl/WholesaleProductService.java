package com.example.productservice.service.impl;

import com.example.productservice.model.entity.WholesaleProduct;
import com.example.productservice.repository.WholesaleProductRepository;
import com.example.productservice.service.IWholesaleProductService;
import com.example.productservice.service.ProductServiceClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WholesaleProductService implements IWholesaleProductService {
    private final WholesaleProductRepository wholesaleProductRepository;
    private final ProductServiceClient productServiceClient;
    private final ObjectMapper objectMapper;


    @Override
    public List<WholesaleProduct> getAllProducts() throws JsonProcessingException, URISyntaxException {
        if (wholesaleProductRepository.findAll().isEmpty()){
            saveWholesaleProducts();
        }
        return wholesaleProductRepository.findAll();
    }

    @Override
    public WholesaleProduct getProductByTitle(String title) throws URISyntaxException, JsonProcessingException {
        if (wholesaleProductRepository.findAll().isEmpty()){
            saveWholesaleProducts();
        }
        return wholesaleProductRepository.findWholesaleProductByTitle(title)
                .orElseThrow(() -> new RuntimeException("Wholesale product not found"));
    }

    public void saveWholesaleProducts() throws JsonProcessingException, URISyntaxException {
        String json=productServiceClient.getProducts();

        JSONObject jsonObject=new JSONObject(json);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        List<WholesaleProduct> products = objectMapper.readValue(jsonObject.getJSONArray("products").toString(),objectMapper.getTypeFactory().constructCollectionType(List.class,WholesaleProduct.class));

        for (WholesaleProduct product : products){
            WholesaleProduct wholeSaleProduct = WholesaleProduct.builder()
                    .brand(product.getBrand())
                    .price(product.getPrice())
                    .rating(product.getRating())
                    .stock(product.getStock())
                    .category(product.getCategory())
                    .title(product.getTitle())
                    .thumbnail(product.getThumbnail())
                    .description(product.getDescription())
                    .build();
            wholesaleProductRepository.save(wholeSaleProduct);
        }
    }
}
