package com.zestt.assign.DTO.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemRequest {

     @NotNull(message = "Quantity must not be null")
    @Min(value = 0, message = "Quantity must be positive")
    private Integer quantity;

}
