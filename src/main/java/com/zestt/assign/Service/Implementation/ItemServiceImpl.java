package com.zestt.assign.Service.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.zestt.assign.DTO.Request.ItemRequest;
import com.zestt.assign.DTO.Response.ItemResponse;
import com.zestt.assign.Entity.Item;
import com.zestt.assign.Entity.Product;
import com.zestt.assign.Exception.ResourceNotFoundException;
import com.zestt.assign.Mapper.Mapper;
import com.zestt.assign.Repository.ItemRepository;
import com.zestt.assign.Repository.ProductRepository;
import com.zestt.assign.Service.ItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final Mapper mapper;

   


    @Override
    public List<ItemResponse> getItemsByProduct(Integer productId) {
        
    //Validate that product exists
    if (!productRepository.existsById(productId)) {
        throw new ResourceNotFoundException("Product not found with id: " + productId);
    }

    //Fetch items
    return itemRepository.findByProductId(productId)
            .stream()
            .map(item -> mapper.mapToResponse(item))
            .collect(Collectors.toList());
    }

    @Override
    public ItemResponse createItem(Integer productId, ItemRequest request) {

        //Validate that product exists
        Product product = productRepository.findById(productId)
        .orElseThrow(()->new ResourceNotFoundException("Product Not Found with Id "+ productId));
    
        //Create item
        Item item=new Item();
        item.setQuantity(request.getQuantity());
        item.setProduct(product);
        Item savedItem=itemRepository.save(item);
    
        return mapper.mapToResponse(savedItem);
    }

}
