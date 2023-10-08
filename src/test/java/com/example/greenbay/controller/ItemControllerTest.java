package com.example.greenbay.controller;

import com.example.greenbay.exception.GlobalExceptionHandler;
import com.example.greenbay.model.Item;
import com.example.greenbay.model.User;
import com.example.greenbay.repository.ItemRepository;
import com.example.greenbay.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Principal mockPrincipal;

    @Autowired
    private ItemController itemController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("John");
    }

    @BeforeAll
    public static void setupOnce(@Autowired UserRepository userRepository,
                                 @Autowired ItemRepository itemRepository) {
        User user1 = new User("John", "john@mail.com", 1000);
        userRepository.save(user1);
        User user2 = new User("Jane", "jane@mail.com", 1000);
        userRepository.save(user2);

        Item item1 = new Item("Name 1", "description 1", 100, null, user1);
        itemRepository.save(item1);
        Item item2 = new Item("Name 2", "description 2", 200, null, user2);
        itemRepository.save(item2);
    }

    @Test
    public void getItemsTest() throws Exception {
        mockMvc.perform(get("/api/item/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    public void getItemByIdTest() throws Exception {
        mockMvc.perform(get("/api/item/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Name 1"));
    }

    @Test
    public void addNewItemTest() throws Exception {
        String requestBody = "{\"name\":\"Name 3\",\"description\":\"description 3\",\"price\":300,\"photo\":\"http://example.com/photo.jpg\"}";

        mockMvc.perform(post("/api/item/")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value("3"))
                        .andExpect(jsonPath("$.name").value("Name 3"))
                        .andExpect(jsonPath("$.seller").value("John"));
    }

    @Test
    public void buyItemTest() throws Exception {
        mockMvc.perform(put("/api/item/2")
                        .principal(mockPrincipal))
                .andExpect(status().isNoContent());
    }

    @Test
    public void buyItemTest_buyerIsSeller_throwsException() throws Exception {
        mockMvc.perform(put("/api/item/1")
                        .principal(mockPrincipal))
                        .andExpect(status().isBadRequest());
    }
}