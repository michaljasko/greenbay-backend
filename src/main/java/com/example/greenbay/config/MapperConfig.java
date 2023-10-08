package com.example.greenbay.config;

import com.example.greenbay.dto.ItemRequestDto;
import com.example.greenbay.dto.ItemResponseDto;
import com.example.greenbay.model.Item;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Configure custom type mapping

        // Get username of seller and buyer from User object
        modelMapper.createTypeMap(Item.class, ItemResponseDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getSeller().getUsername(), ItemResponseDto::setSeller))
                .addMappings(mapper -> mapper.map(src -> src.getBuyer().getUsername(), ItemResponseDto::setBuyer));

        // Skip seller and buyer username when converting DTO to Item object
        modelMapper.createTypeMap(ItemRequestDto.class, Item.class)
                .addMappings(mapper -> mapper.skip(Item::setSeller))
                .addMappings(mapper -> mapper.skip(Item::setBuyer));

        return modelMapper;
    }

}
