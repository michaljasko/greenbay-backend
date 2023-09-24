package com.example.greenbay.model;


import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.List;

@Schema(description = "Class representing a user of the application.")
@Entity
@Table(name = "app_user")
public class User {
    @Schema(description = "Unique identifier of the user.", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Username of the user.", example = "Superman13")
    @Column(nullable = false, unique = true)
    private String username;

    @Schema(description = "Password of the user.", example = "password")
    @Column(nullable = false)
    private String password;

    @Schema(description = "Amount of money owned by the user. Whole number.", example = "100")
    private Integer money;

    @Schema(description = "List of items sold by the user.")
    @OneToMany(mappedBy = "seller", cascade = CascadeType.REMOVE)
    private List<Item> selling;

    @Schema(description = "List of items bought by the user.")
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.REMOVE)
    private List<Item> buying;

    // Constructors
    public User() {
    }

    public User(String username, String password, Integer money) {
        this.username = username;
        this.password = password;
        this.money = money;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public List<Item> getSelling() {
        return selling;
    }

    public void setSelling(List<Item> selling) {
        this.selling = selling;
    }

    public List<Item> getBuying() {
        return buying;
    }

    public void setBuying(List<Item> buying) {
        this.buying = buying;
    }
}
