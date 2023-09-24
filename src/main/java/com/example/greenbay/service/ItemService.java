package com.example.greenbay.service;

import com.example.greenbay.dto.ItemRequestDto;
import com.example.greenbay.dto.ItemResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    List<ItemResponseDto> getItems();

    ItemResponseDto getItemById(Long itemId);

    ItemResponseDto addNewItem(ItemRequestDto itemDto, String username);

    void buyItem(Long itemId, String username);

    String savePhoto(MultipartFile photo);
}
