package com.example.productservice.service.impl;

import com.example.productservice.model.dto.request.DepositSellPriceDto;
import com.example.productservice.model.entity.MarketProduct;
import com.example.productservice.repository.MarketProductRepository;
import com.example.productservice.service.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final MarketProductRepository marketProductRepository;
    private final MailSenderService mailSenderService;
    private final UserServiceClient userServiceClient;


    @Scheduled(initialDelay = 10 * 60 *1000, fixedRate = 20 * 60 *1000) //Every  20 minute
    public void chaneSellStatus(){
        List<MarketProduct> products = marketProductRepository.findAllByStatusIsTrue();

        if (products.isEmpty()){
            return;
        }
        MarketProduct randomProduct = setRandomProduct(products);
        updateUserBalance(randomProduct);

        mailSenderService.sendProductUpdateMail("", randomProduct.getTitle());
    }

    private MarketProduct setRandomProduct(List<MarketProduct> products) {
        Random random=new Random();
        MarketProduct randomProduct= products.get(random.nextInt(products.size()));
        randomProduct.setStatus(true);
        randomProduct.setStatus(false);

        marketProductRepository.save(randomProduct);
        return randomProduct;
    }

    private void updateUserBalance(MarketProduct randomProduct) {
        DepositSellPriceDto depositSellPriceDto = DepositSellPriceDto.builder()
                .money(randomProduct.getPrice())
                .username(randomProduct.getUser().getUsername())
                .build();
        userServiceClient.depositMoneyWithSelling(depositSellPriceDto);
    }
}
