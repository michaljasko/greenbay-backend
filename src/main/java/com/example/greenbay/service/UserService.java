package com.example.greenbay.service;

import com.example.greenbay.dto.AuthRequestDto;
import com.example.greenbay.dto.UserDto;


public interface UserService {
    boolean signUp(AuthRequestDto signUpRequest);

    boolean login(AuthRequestDto loginRequest);

    UserDto getUserDtoByUsername(String username);
}
