package com.example.greenbay.controller;

import com.example.greenbay.dto.AuthRequestDto;
import com.example.greenbay.dto.UserDto;
import com.example.greenbay.service.UserService;
import com.example.greenbay.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@Tag(name = "User/Auth management", description = "Endpoints for managing users and their authentication")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "User Sign up", description = "Create new user account",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User signed up successfully"),
                    @ApiResponse(responseCode = "400", description = "Failed to sign up user")
            })
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody AuthRequestDto signUpRequest) {
        boolean isSignedUp = userService.signUp(signUpRequest);

        if (isSignedUp) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User signed up successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to sign up user");
        }
    }

    @Operation(summary = "User Login", description = "Authenticate user with username and password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful",
                            content = @Content(schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication failed")
            })
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequestDto loginRequest) {
        boolean isLoggedIn = userService.login(loginRequest);

        if (isLoggedIn) {
            UserDto user = userService.getUserDtoByUsername(loginRequest.getUsername());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }

            String token = jwtUtil.generateToken(user);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", user.getUsername());
            response.put("id", user.getId());
            response.put("money", user.getMoney());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
