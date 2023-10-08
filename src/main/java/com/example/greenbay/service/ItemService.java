package com.example.greenbay.service;

import com.example.greenbay.dto.ItemRequestDto;
import com.example.greenbay.dto.ItemResponseDto;

import java.util.List;

public interface ItemService {
    List<ItemResponseDto> getItems();

    ItemResponseDto getItemById(Long itemId);

    ItemResponseDto addNewItem(ItemRequestDto itemDto, String username);

    void buyItem(Long itemId, String username);
}
