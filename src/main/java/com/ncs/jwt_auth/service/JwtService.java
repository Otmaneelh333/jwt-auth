package com.ncs.jwt_auth.service;

import com.ncs.jwt_auth.model.OrderToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // pour test local

    public String generateToken(OrderToken tokenInfo) {
        return Jwts.builder()
                .setSubject("tracking_token")
                .claim("orderId", tokenInfo.getOrderId())
                .claim("userId", tokenInfo.getUserId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1j
                .signWith(secretKey)
                .compact();
    }

    public boolean isTokenValid(String token, String orderId) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(token).getBody();

            return orderId.equals(claims.get("orderId", String.class));
        } catch (JwtException e) {
            return false;
        }
    }
}