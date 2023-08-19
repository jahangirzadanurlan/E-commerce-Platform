package com.example.productservice.service.impl;

import com.example.productservice.model.dto.request.ProductDto;
import com.example.productservice.model.dto.request.ProductRequestDto;
import com.example.productservice.model.dto.response.UserResponseDto;
import com.example.productservice.model.entity.MarketProduct;
import com.example.productservice.model.entity.User;
import com.example.productservice.model.entity.WholesaleProduct;
import com.example.productservice.repository.MarketProductRepository;
import com.example.productservice.repository.UserRepository;
import com.example.productservice.service.IMarketProductService;
import com.example.productservice.service.IWholesaleProductService;
import com.example.productservice.service.UserServiceClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MarketProductService implements IMarketProductService {
    private final MarketProductRepository marketProductRepository;
    private final IWholesaleProductService wholesaleProductService;
    private final UserServiceClient userServiceClient;
    private final UserRepository userRepository;

    @Override
    public List<MarketProduct> getAllProducts() {
        return marketProductRepository.findAllByStatusIsTrue();
    }

    @Override
    public MarketProduct getProductByTitle(String title) {
        return marketProductRepository.findMarketProductByTitle(title)
                .orElseThrow(() -> new RuntimeException("Product not found!"));
    }

    @Override
    public String saveProduct(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,ProductRequestDto productRequestDto) throws URISyntaxException, JsonProcessingException {
        WholesaleProduct product = wholesaleProductService.getProductByTitle(productRequestDto.getProductTitle());

        if (product.getStock() < productRequestDto.getStockCount()){
            throw new RuntimeException("Stock count not enough for your offer");
        }
        ProductDto productDto = ProductDto.builder()
                .sellPrice(productRequestDto.getSellPrice())
                .productPrice(product.getPrice())
                .stockCount(productRequestDto.getStockCount())
                .build();
        String save = save(token, productDto, product);
        if (save != null) return save;
        throw new RuntimeException("User Not found");
    }

    private String save(String token, ProductDto productDto, WholesaleProduct product) {
        UserResponseDto userDto = userServiceClient.checkUser(token, productDto);

        if (userDto != null){
            if (userRepository.findUserByUsernameOrEmail(userDto.getUsername()).isEmpty()){
                User user = User.builder()
                        .email(userDto.getEmail())
                        .username(userDto.getUsername())
                        .email(userDto.getEmail())
                        .password(userDto.getPassword())
                        .build();

                userRepository.save(user);
            }
            createMarketProduct(productDto, product, userDto);
            return "Save is successfully!";
        }
        return null;
    }

    private void createMarketProduct(ProductDto productDto, WholesaleProduct product, UserResponseDto userDto) {
        Optional<User> _user = userRepository.findUserByUsernameOrEmail(userDto.getUsername());

        MarketProduct marketProduct = MarketProduct.builder()//MapStruct islet
                .brand(product.getBrand())
                .price(productDto.getSellPrice())
                .rating(product.getRating())
                .stock(productDto.getStockCount())
                .category(product.getCategory())
                .title(product.getTitle())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .user(_user.orElseThrow())
                .build();
        marketProductRepository.save(marketProduct);

        List<MarketProduct> productList = Optional.ofNullable(_user.get().getMarketProduct()).orElse(new ArrayList<>());
        productList.add(marketProduct);
        _user.get().setMarketProduct(productList);
        userRepository.save(_user.get());
    }

    @Override
    public void deleteProduct(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,Long id) {
        User user = userServiceClient.checkHasUser(token);

        if(user != null){
            Optional<MarketProduct> product = marketProductRepository.findById(id);

            if (product.get().getUser().getUsername().equals(user.getUsername())){
                product.orElseThrow(() -> new RuntimeException("Product not found!"))
                        .setStatus(false);
                marketProductRepository.save(product.get());
            }else {
                throw new RuntimeException("User does not have the right to delete");
            }
        }else {
            throw new RuntimeException("User not found exception!");
        }
    }
}
