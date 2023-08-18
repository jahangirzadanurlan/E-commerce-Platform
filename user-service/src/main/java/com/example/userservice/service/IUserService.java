package com.example.userservice.service;


import com.example.userservice.model.dto.request.DepositRequestDto;
import com.example.userservice.model.dto.request.ProductDto;
import com.example.userservice.model.dto.response.ResponseDto;
import com.example.userservice.model.dto.response.UserResponseDto;
import com.example.userservice.model.entity.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<ResponseDto> depositMoney(DepositRequestDto depositRequestDto);
    UserResponseDto checkUser(String authHeader, ProductDto productDto);
    User checkHasUser(String authHeader);
}
