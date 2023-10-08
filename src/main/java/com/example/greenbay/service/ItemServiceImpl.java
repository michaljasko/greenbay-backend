package com.example.greenbay.service;

import com.example.greenbay.dto.ItemRequestDto;
import com.example.greenbay.dto.ItemResponseDto;
import com.example.greenbay.exception.ValidationException;
import com.example.greenbay.model.Item;
import com.example.greenbay.model.User;
import com.example.greenbay.repository.ItemRepository;
import com.example.greenbay.repository.UserRepository;
import com.example.greenbay.validator.ItemRequestDtoValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ItemRequestDtoValidator itemRequestDtoValidator;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository,
                           UserRepository userRepository,
                           ModelMapper modelMapper,
                           ItemRequestDtoValidator itemRequestDtoValidator) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.itemRequestDtoValidator = itemRequestDtoValidator;
    }


    @Override
    public List<ItemResponseDto> getItems() {
        return itemRepository.findAll()
                .stream()
                .map(item -> modelMapper.map(item, ItemResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponseDto getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id " + itemId));
        return modelMapper.map(item, ItemResponseDto.class);
    }

    @Override
    public ItemResponseDto addNewItem(ItemRequestDto itemDto, String username) {
        Errors errors = new BeanPropertyBindingResult(itemDto, "ItemRequestDto");
        itemRequestDtoValidator.validate(itemDto, errors);

        if (errors.hasErrors()) {
            throw new ValidationException("Validation failed for MealRequestDto", errors);
        }

        User seller = userRepository.findByUsername(username);

        Item item = modelMapper.map(itemDto, Item.class);
        item.setSeller(seller);

        Item newItem = itemRepository.save(item);
        return modelMapper.map(newItem, ItemResponseDto.class);
    }

    @Override
    public void buyItem(Long itemId, String username) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id " + itemId));

        User buyer = userRepository.findByUsername(username);
        User seller = item.getSeller();

        if (Objects.equals(seller.getUsername(), username)) {
            throw new RuntimeException("You cannot buy your own item.");
        }
        if (item.getBuyer() != null) {
            throw new RuntimeException("This item was already sold.");
        }
        if (item.getPrice() > buyer.getMoney()) {
            throw new RuntimeException("Not enough money.");
        }

        item.setBuyer(buyer);
        itemRepository.save(item);

        buyer.setMoney(buyer.getMoney() - item.getPrice());
        userRepository.save(buyer);
        seller.setMoney(seller.getMoney() + item.getPrice());
        userRepository.save(seller);
    }
}
