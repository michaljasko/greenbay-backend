package com.example.greenbay.controller;

import com.example.greenbay.dto.ItemRequestDto;
import com.example.greenbay.dto.ItemResponseDto;
import com.example.greenbay.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Tag(name = "Item management", description = "Endpoints for managing items for sale")
@RestController
@RequestMapping(path = "api/item")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "Get a list of all items")
    @GetMapping("/")
    public List<ItemResponseDto> getItems() {
        return itemService.getItems();
    }

    @Operation(summary = "Get a single item by its ID")
    @GetMapping("/{itemId}")
    public ItemResponseDto getItemById(@PathVariable Long itemId) {
        return itemService.getItemById(itemId);
    }

    @Operation(summary = "Add a new item available for sale", description = "Provide item details and photo", responses = {
            @ApiResponse(responseCode = "200", description = "Created item object", content = @Content(schema = @Schema(implementation = ItemResponseDto.class)))
    })
    @PostMapping("/")
    public ItemResponseDto addNewItem(@RequestBody ItemRequestDto itemDto, Principal user) {
        String username = user.getName();
        return itemService.addNewItem(itemDto, username);
    }

    @Operation(summary = "Buy the item", description = "Provide item ID")
    @PutMapping("/{id}")
    public ResponseEntity<Void> buyItem(@PathVariable Long id, Principal user) {
        String username = user.getName();
        itemService.buyItem(id, username);
        return ResponseEntity.noContent().build();
    }
}
