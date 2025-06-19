package com.ncs.jwt_auth.controller;

import com.ncs.jwt_auth.model.OrderToken;
import com.ncs.jwt_auth.repository.OrderTokenRepository;
import com.ncs.jwt_auth.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private OrderTokenRepository repository;

    @PostMapping("/generate")
    public ResponseEntity<?> generate(@RequestBody OrderToken tokenInfo) {
        repository.save(tokenInfo);
        String token = jwtService.generateToken(tokenInfo);
        return ResponseEntity.ok().body(Map.of("token", token));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(
            @RequestParam String token,
            @RequestParam String orderId
    ) {
        boolean isValid = jwtService.isTokenValid(token, orderId);
        return ResponseEntity.ok().body(Map.of("valid", isValid));
    }
}