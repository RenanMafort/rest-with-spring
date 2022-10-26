package com.example.curseforbradesco.controllers;

import com.example.curseforbradesco.data.vo.v1.security.AccountCredentialsVO;
import com.example.curseforbradesco.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthServices authServices;

    @Operation(summary = "Authenticates a user and returns a token")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
        if (checkParamIsNoNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client");

        var token = authServices.signin(data);
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client");

        return token;
    }

    @Operation(summary = "Refresh token for authenticated user and returns a token")
    @PutMapping(value = "/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable("username") String username,
                                       @RequestHeader("Authorization") String refreshToken) {
        if (checkParamIsNoNull(username,refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client");

        var token = authServices.refreshToken(username, refreshToken);
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client");

        return token;
    }


        private boolean checkParamIsNoNull(String username, String refreshToken){
            return refreshToken == null || refreshToken.isBlank() ||
                    username == null || username.isBlank();
        }

        private boolean checkParamIsNoNull(AccountCredentialsVO data){
            return data == null || data.getUsername() == null || data.getUsername().isBlank()
                    || data.getPassword() == null || data.getPassword().isBlank();
        }
    }
