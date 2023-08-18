package com.example.userservice.service.impl;

import com.example.userservice.model.dto.response.ResponseDto;
import com.example.userservice.model.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService implements IWalletService {
    private final UserRepository userRepository;


    @Override
    public ResponseDto increaseBalance(double price, User user) {
        double wallet = user.getWallet();
        user.setWallet(wallet + price);

        userRepository.save(user);
        return new ResponseDto(price + " dollar loaded into the wallet");
    }

    @Override
    public ResponseDto decreaseBalance(double price, User user) {
        double wallet = user.getWallet();
        user.setWallet(wallet - price);

        userRepository.save(user);
        return new ResponseDto(price + " was deducted from the wallet");
    }

    @Override
    public boolean checkBalance(int offerPrice,User user) {
        return user.getWallet() >= offerPrice;
    }
}
