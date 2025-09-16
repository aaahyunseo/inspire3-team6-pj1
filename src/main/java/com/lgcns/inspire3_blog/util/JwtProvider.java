package com.lgcns.inspire3_blog.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider { 
    
    @Value("${jwt.secret}")
    private String secret;

    private final long ACCESS_TOKEN_EXPIRY = 1000L * 60 * 30;       // 30분
    private final long REFRESH_TOKEN_EXPIRY = 1000L * 60 * 60 * 24 * 7; // 7일

    private Key getStringKey() {
        System.out.println("[debug] >>> JWTProvider Secret : " + secret);
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String email) {
        System.out.println("[debug] >>> JwtProvider generateAccessToken");
        return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))
                    .signWith(getStringKey())
                    .compact();
    }

    public String generateRefreshToken(String email) {
        System.out.println("[debug] >>> JwtProvider generateRefreshToken");
        return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24*7))
                    .signWith(getStringKey())
                    .compact();
    }

    public String getUserIdFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거
        }

        Claims claims = Jwts.parser()
                .setSigningKey(getStringKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // JWT의 subject에 email 저장했다고 가정
    }

    // Refresh Token 만료시간 반환
    public long getRefreshTokenExpiry() {
        return REFRESH_TOKEN_EXPIRY;
    }

    // Access Token 만료시간 반환 (필요시)
    public long getAccessTokenExpiry() {
        return ACCESS_TOKEN_EXPIRY;
    }
}