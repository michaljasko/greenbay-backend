package com.example.greenbay.service;

import com.example.greenbay.dto.AuthRequestDto;
import com.example.greenbay.dto.UserDto;
import com.example.greenbay.model.User;
import com.example.greenbay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public boolean signUp(AuthRequestDto signUpRequest) {
        String username = signUpRequest.getUsername();
        String password = signUpRequest.getPassword();
        Integer initialMoney = 10000;

        User existingUser = userRepository.findByUsername(username);

        if (existingUser == null) {
            String encodedPassword = passwordEncoder.encode(password);
            User user = new User(username, encodedPassword, initialMoney);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean login(AuthRequestDto loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userRepository.findByUsername(username);

        if (user != null) {
            String storedEncodedPassword = user.getPassword();
            return passwordEncoder.matches(password, storedEncodedPassword);
        }
        return false;
    }

    @Override
    public UserDto getUserDtoByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new UserDto(user.getUsername(), user.getId(), user.getMoney());
        }
        return null;
    }
}
