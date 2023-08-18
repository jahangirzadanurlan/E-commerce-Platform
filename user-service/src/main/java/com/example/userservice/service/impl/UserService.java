package com.example.userservice.service.impl;

import com.example.userservice.exception.ApplicationException;
import com.example.userservice.model.dto.request.DepositRequestDto;
import com.example.userservice.model.dto.request.ProductDto;
import com.example.userservice.model.dto.response.ResponseDto;
import com.example.userservice.model.dto.response.UserResponseDto;
import com.example.userservice.model.entity.User;
import com.example.userservice.model.enums.Exceptions;
import com.example.userservice.repository.TokenRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.SecurityHelper;
import com.example.userservice.service.IUserService;
import com.example.userservice.service.IWalletService;
import com.example.userservice.service.impl.security.impl.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IWalletService walletService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final SecurityHelper securityHelper;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;

    @Override
    public ResponseEntity<ResponseDto> depositMoney(DepositRequestDto depositRequestDto) {
        Optional<User> user = userRepository.findUserByUsernameOrEmail(depositRequestDto.getUsername());

        if (passwordEncoder.matches(depositRequestDto.getPassword(),user.orElseThrow(() -> new ApplicationException(Exceptions.USER_NOT_FOUND_EXCEPTION)).getPassword())){
            walletService.increaseBalance(depositRequestDto.getMoney(),user.orElseThrow());
            return ResponseEntity.ok().body(new ResponseDto(depositRequestDto.getMoney() + " was added to " + depositRequestDto.getUsername() + " balance"));
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto("User Password is wrong! "));
        }
    }

    public UserResponseDto checkUser(String authHeader, ProductDto productDto) {
        if (!securityHelper.authHeaderIsValid(authHeader)) {
            throw new RuntimeException();
        }

        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);

        if (username != null) {
            User user = userRepository.findUserByUsernameOrEmail(username)
                    .orElseThrow(() -> new RuntimeException("Username doesn't exist: " + username));

            if (!user.isEnabled()){
                throw new RuntimeException(username+" your account not enabled.Please verify your account!");
            }

            tokenRepository.findTokenByToken(jwt)
                    .orElseThrow(() -> new RuntimeException("Token doesn't exist: " + user));

            return checkUserWallet(productDto, jwt, user);

        }else {
            throw new ApplicationException(Exceptions.TOKEN_IS_INVALID_EXCEPTION);
        }
    }

    private UserResponseDto checkUserWallet(ProductDto productDto, String jwt, User user) {
        if (jwtService.isTokenValid(jwt, user)) {
            double price = productDto.getProductPrice() * productDto.getStockCount();
            if (user.getWallet() >= price ){
                walletService.decreaseBalance(price, user);

                mailSenderService.sendBuyMessage(user,price);
                return UserResponseDto.builder()
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .build();
            }else {
                throw new ApplicationException(Exceptions.WALLET_NOT_ENOUGH_EXCEPTION);
            }
        } else {
            throw new ApplicationException(Exceptions.TOKEN_IS_INVALID_EXCEPTION);
        }
    }

    public User checkHasUser(String authHeader){
        if (!securityHelper.authHeaderIsValid(authHeader)) {
            throw new RuntimeException();
        }

        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);

        if (username != null) {
            User user = userRepository.findUserByUsernameOrEmail(username)
                    .orElseThrow(() -> new ApplicationException(Exceptions.USER_NOT_FOUND_EXCEPTION));

            if (!user.isEnabled()){
                throw new RuntimeException(username+" your account not enabled.Please verify your account!");
            }

            tokenRepository.findTokenByToken(jwt)
                    .orElseThrow(() -> new RuntimeException("Token doesn't exist: " + user));
            return user;

        }else {
            throw new ApplicationException(Exceptions.TOKEN_IS_INVALID_EXCEPTION);
        }
    }

}
