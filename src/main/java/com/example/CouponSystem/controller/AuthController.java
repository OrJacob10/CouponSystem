package com.example.CouponSystem.controller;

import com.example.CouponSystem.auth.LoginRequestDTO;
import com.example.CouponSystem.auth.TokenResponseDTO;
import com.example.CouponSystem.exception.AuthorizationException;
import com.example.CouponSystem.service.AuthService;
import com.example.CouponSystem.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) throws AuthorizationException {
        return this.authService.createTokenFromLoginDetails(loginRequestDTO);
    }
}
