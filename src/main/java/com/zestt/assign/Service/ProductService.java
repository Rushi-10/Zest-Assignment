package com.zestt.assign.Service;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zestt.assign.DTO.Request.ProductRequest;
import com.zestt.assign.DTO.Response.ItemResponse;
import com.zestt.assign.DTO.Response.ProductResponse;



public interface ProductService {
Page<ProductResponse> getAllProducts(Pageable pageable);

    ProductResponse getProductById(Integer id);

    ProductResponse createProduct(ProductRequest request,String username);

    ProductResponse updateProduct(Integer id, ProductRequest request);

    void deleteProduct(Integer id);

    List<ItemResponse> getItemsByProduct(Integer productId);
}
