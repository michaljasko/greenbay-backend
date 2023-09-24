package com.example.greenbay.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ItemRequestDto {
    @Schema(description = "Name of the item.", example = "iPhone 39")
    private String name;

    @Schema(description = "Description of the item.", example = "Device from the future, mint condition.")
    private String description;

    @Schema(description = "Price of the item as a whole number.", example = "899")
    private Integer price;

    @Schema(description = "Item image path.")
    private String photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
