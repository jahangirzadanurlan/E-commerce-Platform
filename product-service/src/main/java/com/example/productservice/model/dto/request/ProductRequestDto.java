package com.example.productservice.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequestDto {
    @NotNull(message = "Product title cannot be null")
    String productTitle;

    @NotNull(message = "Sell price cannot be null")
    @Min(value = 0,message = "Must be positive")
    Integer sellPrice;

    @NotNull(message = "Stock count cannot be null")
    @Min(value = 0,message = "Must be positive")
    Integer stockCount;

}
