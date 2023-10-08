package com.example.greenbay.service;

import com.example.greenbay.dto.ItemRequestDto;
import com.example.greenbay.dto.ItemResponseDto;
import com.example.greenbay.model.Item;
import com.example.greenbay.model.User;
import com.example.greenbay.repository.ItemRepository;
import com.example.greenbay.repository.UserRepository;
import com.example.greenbay.validator.ItemRequestDtoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.validation.Errors;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ItemRequestDtoValidator itemRequestDtoValidator;

    @Mock
    private Errors errors;

    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemService = new ItemServiceImpl(itemRepository, userRepository, modelMapper,itemRequestDtoValidator);
    }

    @Test
    void getItems() {
        itemService.getItems();

        verify(itemRepository).findAll();
    }

    @Test
    void getItemById_whenItemExists() {
        Item item = new Item();
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        itemService.getItemById(1L);

        verify(itemRepository).findById(1L);
    }

    @Test
    void getItemById_whenItemNotExists() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> itemService.getItemById(1L));
    }

    @Test
    void addNewItem() {
        ItemRequestDto itemDto = new ItemRequestDto();
        User seller = new User();
        Item savedItem = new Item();
        Item item = new Item();
        item.setSeller(seller);

        when(userRepository.findByUsername("testUsername")).thenReturn(seller);
        when(itemRepository.save(any(Item.class))).thenReturn(savedItem);
        when(modelMapper.map(savedItem, ItemResponseDto.class)).thenReturn(new ItemResponseDto());
        when(modelMapper.map(itemDto, Item.class)).thenReturn(item);
        doNothing().when(itemRequestDtoValidator).validate(itemDto, errors);

        itemService.addNewItem(itemDto, "testUsername");

        verify(itemRepository).save(any(Item.class));
    }

    @Test()
    public void buyItem_itemNotFound_throwsException() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> itemService.buyItem(1L, "buyerUsername"));
    }

    @Test()
    public void buyItem_buyerIsSeller_throwsException() {
        Item item = new Item();
        User seller = new User();
        seller.setUsername("buyerUsername");
        item.setSeller(seller);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(userRepository.findByUsername("buyerUsername")).thenReturn(seller);

        assertThrows(RuntimeException.class, () -> itemService.buyItem(1L, "buyerUsername"));
    }

    @Test()
    public void buyItem_itemAlreadySold_throwsException() {
        Item item = new Item();
        item.setBuyer(new User());  // Setting buyer indicates item was already sold

        User seller = new User();
        item.setSeller(seller);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        assertThrows(RuntimeException.class, () -> itemService.buyItem(1L, "buyerUsername"));
    }

    @Test()
    public void buyItem_notEnoughMoney_throwsException() {
        Item item = new Item();
        item.setPrice(1000);

        User buyer = new User();
        buyer.setMoney(500);  // Buyer has less money than item's price

        User seller = new User();
        item.setSeller(seller);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(userRepository.findByUsername("buyerUsername")).thenReturn(buyer);

        assertThrows(RuntimeException.class, () -> itemService.buyItem(1L, "buyerUsername"));
    }

    @Test
    public void buyItem_successfulPurchase() {
        Item item = new Item();
        item.setPrice(500);

        User buyer = new User();
        buyer.setMoney(1000);

        User seller = new User();
        seller.setMoney(100);
        item.setSeller(seller);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(userRepository.findByUsername("buyerUsername")).thenReturn(buyer);

        itemService.buyItem(1L, "buyerUsername");

        assertEquals(500, buyer.getMoney());  // Check if buyer's money is deducted
        assertEquals(buyer, item.getBuyer());  // Check if buyer is set to the item

        verify(itemRepository).save(item);  // Verify item is saved after purchase
        verify(userRepository).save(buyer);  // Verify buyer details are updated
    }
}