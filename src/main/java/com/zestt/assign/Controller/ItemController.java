package com.zestt.assign.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zestt.assign.DTO.Request.ItemRequest;
import com.zestt.assign.DTO.Response.ItemResponse;
import com.zestt.assign.Service.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/v1/products/{productId}/items")
@RequiredArgsConstructor
public class ItemController {


   private final ItemService itemService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<List<ItemResponse>> getItems(
            @PathVariable Integer productId) {

        return ResponseEntity.ok(
                itemService.getItemsByProduct(productId)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
      public ResponseEntity<ItemResponse> createItem(
        @PathVariable Integer productId,
        @Valid @RequestBody ItemRequest request) {

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(itemService.createItem(productId, request));
}
}
