package com.example.greenbay.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Schema(description = "Class representing an item available to sale.")
@Entity
@Table(name = "app_item")
public class Item {
    @Schema(description = "Unique identifier of the item.", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Name of the item.", example = "iPhone 39")
    private String name;

    @Schema(description = "Description of the item.", example = "Device from the future, mint condition.")
    @Column(columnDefinition = "TEXT")
    private String description;

    @Schema(description = "Price of the item as a whole number.", example = "899")
    private Integer price;

    @Schema(description = "Item image path.")
    private String photo;

    @Schema(description = "ID of the User, who sells this item.", example = "1")
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @Schema(description = "ID of the User, who bought this item.", example = "2")
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    // Constructors

    public Item(String name, String description, Integer price, String photo, User seller) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.photo = photo;
        this.seller = seller;
    }

    // Getters and setters

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

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
}
