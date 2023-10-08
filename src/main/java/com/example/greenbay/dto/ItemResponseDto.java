package com.example.greenbay.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ItemResponseDto {
    @Schema(description = "Unique identifier of the item.", example = "1")
    private Long id;

    @Schema(description = "Name of the item.", example = "iPhone 39")
    private String name;

    @Schema(description = "Description of the item.", example = "Device from the future, mint condition.")
    private String description;

    @Schema(description = "Price of the item as a whole number.", example = "899")
    private Integer price;

    @Schema(description = "Item image url.")
    private String photo;

    @Schema(description = "Username of the User, who sells this item.", example = "1")
    private String seller;

    @Schema(description = "Username of the User, who bought this item.", example = "2")
    private String buyer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }
}
