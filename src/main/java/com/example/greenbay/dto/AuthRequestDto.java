package com.example.greenbay.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthRequestDto {
    @Schema(description = "Username of the user.", example = "Superman13")
    private String username;

    @Schema(description = "Password of the user.", example = "password")
    private String password;

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
}
