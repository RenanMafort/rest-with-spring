package com.example.curseforbradesco.services;

import com.example.curseforbradesco.data.vo.v1.security.AccountCredentialsVO;
import com.example.curseforbradesco.data.vo.v1.security.TokenVO;
import com.example.curseforbradesco.repositories.UserRepository;
import com.example.curseforbradesco.security.Jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    public ResponseEntity signin(AccountCredentialsVO data){
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

            var user = repository.findByUserName(username);

            var tokenResponse = new TokenVO();
            if (user != null){
                tokenResponse = tokenProvider.createAccesToken(username,user.getRoles());

            }else {
                throw new UsernameNotFoundException("Username " + username + "not found! ");
            }

            return ResponseEntity.ok(tokenResponse);

        }catch (Exception e ){
            throw  new BadCredentialsException("Invalid username/password supplied!");
        }
    }

    public ResponseEntity refreshToken(String username,String refreshToken){

        var user = repository.findByUserName(username);

        var tokenResponse = new TokenVO();
        if (user != null){
            tokenResponse = tokenProvider.refreshToken(refreshToken);

        }else {
            throw new UsernameNotFoundException("Username " + username + "not found! ");
        }

        return ResponseEntity.ok(tokenResponse);

    }
}
