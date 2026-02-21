package com.zestt.assign.Service.Implementation;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zestt.assign.DTO.Request.ProductRequest;
import com.zestt.assign.DTO.Response.ItemResponse;
import com.zestt.assign.DTO.Response.ProductResponse;
import com.zestt.assign.Entity.Product;
import com.zestt.assign.Exception.ResourceNotFoundException;
import com.zestt.assign.Mapper.Mapper;
import com.zestt.assign.Repository.ItemRepository;
import com.zestt.assign.Repository.ProductRepository;
import com.zestt.assign.Service.ProductService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final Mapper mapper;


  
    


    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(mapper::mapToResponse);
    }

    @Override
    public ProductResponse getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return mapper.mapToResponse(product);
    }

    @Override
    public ProductResponse createProduct(ProductRequest request,String username) {
        
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setCreatedBy(username);
        product.setCreatedOn(LocalDateTime.now());

        Product saved = productRepository.save(product);
        return mapper.mapToResponse(saved);
    }

    @Override
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

   



    @Override
    public ProductResponse updateProduct(Integer id, ProductRequest request) {
      Product product = productRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Product not found with id: " + id)
            );

    product.setProductName(request.getProductName());

    product.setModifiedBy("SYSTEM"); // later replace with logged-in user
    product.setModifiedOn(LocalDateTime.now());

    Product updatedProduct = productRepository.save(product);

    return mapper.mapToResponse(updatedProduct);
    }


    @Override
    public List<ItemResponse> getItemsByProduct(Integer productId) {
         if (!productRepository.existsById(productId)) {
        throw new ResourceNotFoundException(
                "Product not found with id: " + productId
        );
    }

    return itemRepository.findByProductId(productId)
            .stream()
            .map(item -> new ItemResponse(
                    item.getId(),
                    item.getQuantity()
            ))
            .toList();
}
    

}
