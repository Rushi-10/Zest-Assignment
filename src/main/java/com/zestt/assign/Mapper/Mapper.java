package com.zestt.assign.Mapper;

import org.springframework.stereotype.Component;

import com.zestt.assign.DTO.Response.ItemResponse;
import com.zestt.assign.DTO.Response.ProductResponse;
import com.zestt.assign.Entity.Item;
import com.zestt.assign.Entity.Product;



@Component
public class Mapper {

    public ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProductName()
        );
    }

    public ItemResponse mapToResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getQuantity()
        );
    }
}
