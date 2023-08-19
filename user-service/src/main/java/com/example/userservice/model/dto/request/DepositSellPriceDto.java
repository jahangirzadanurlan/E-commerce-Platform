package com.example.userservice.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepositSellPriceDto {
    @NotBlank(message = "Username is required")
    String username;

    @Min(value = 0, message = "Money must be a positive value")
    int money;
}
