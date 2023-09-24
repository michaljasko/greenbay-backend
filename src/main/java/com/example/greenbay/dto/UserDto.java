package com.example.greenbay.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserDto {
    @Schema(description = "Username of the user.", example = "Superman13")
    private String username;

    @Schema(description = "Unique identifier of the user.", example = "1")
    private Long id;

    public UserDto(String username, Long id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
