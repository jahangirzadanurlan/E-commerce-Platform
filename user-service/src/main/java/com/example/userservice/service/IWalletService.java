package com.example.userservice.service;


import com.example.userservice.model.dto.response.ResponseDto;
import com.example.userservice.model.entity.User;

public interface IWalletService {
    ResponseDto increaseBalance(double price, User user);
    ResponseDto decreaseBalance(double price,User user);

    boolean checkBalance(int offerPrice, User user);

}

