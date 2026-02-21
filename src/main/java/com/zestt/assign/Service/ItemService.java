package com.zestt.assign.Service;

import java.util.List;

import com.zestt.assign.DTO.Request.ItemRequest;
import com.zestt.assign.DTO.Response.ItemResponse;



public interface ItemService  {

    public List<ItemResponse> getItemsByProduct(Integer productId);

    public ItemResponse createItem(Integer productId, ItemRequest request);


}
