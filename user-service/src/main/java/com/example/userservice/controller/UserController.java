package com.example.userservice.controller;

import com.example.userservice.model.dto.request.DepositRequestDto;
import com.example.userservice.model.dto.request.DepositSellPriceDto;
import com.example.userservice.model.dto.request.ProductDto;
import com.example.userservice.model.dto.response.ResponseDto;
import com.example.userservice.model.dto.response.UserResponseDto;
import com.example.userservice.model.entity.User;
import com.example.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class UserController {
    private final IUserService userService;

    @PostMapping("/check")
    public UserResponseDto checkUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody ProductDto productDto) {
        return userService.checkUser(authHeader, productDto );

    }

    @PostMapping("/check-user")
    public User checkHasUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return userService.checkHasUser(authHeader);
    }

    @PutMapping("/deposit")
    public ResponseEntity<ResponseDto> depositMoney(@RequestBody DepositRequestDto depositRequestDto){
        return userService.depositMoney(depositRequestDto);
    }

    @PutMapping("/sell-deposit")
    public ResponseEntity<ResponseDto> depositMoneyWithSelling(@RequestBody DepositSellPriceDto depositSellPriceDto){
        return userService.depositMoneyWithSelling(depositSellPriceDto);
    }
}
