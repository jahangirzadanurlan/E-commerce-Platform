package com.example.productservice.service;

import com.example.productservice.model.dto.request.ProductDto;
import com.example.productservice.model.dto.response.UserResponseDto;
import com.example.productservice.model.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service",url = "http://localhost:8081/auth")
public interface UserServiceClient {

    @PostMapping("/check")
    UserResponseDto checkUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody ProductDto productDto);

    @PostMapping("/check-user")
    User checkHasUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader);
}
