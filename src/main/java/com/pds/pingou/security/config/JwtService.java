package com.pds.pingou.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${api.security.token.secret}")
    private String secret_key;
    @Value("${api.security.token.expiration}")
    private long jwtExpiration;
    @Value("${api.security.token.refresh-token.expiration}")
    private long refreshExpiration;
    @Value("${api.security.token.refresh-token.secret}")
    private String refreshSecretKey;


    public String extraiUsername(String token) {
        return extractClaim(token, Claims::getSubject, getSignKey());
    }

    public String geradorToken(UserDetails userDetails) {
        return geradorToken(new HashMap<>(), userDetails);
    }

    public String geradorToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration, getSignKey());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extraiUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpirado(token, getSignKey());
    }


    public String extraiUsernameRefreshToken(String token) {
        return extractClaim(token, Claims::getSubject, getSignKeyRefresh());
    }

    public String geradorRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration, getSignKeyRefresh());
    }

    public boolean isTokenValidRefresh(String token, UserDetails userDetails) {
        final String username = extraiUsernameRefreshToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpirado(token, getSignKeyRefresh());
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver, SecretKey key) {
        final Claims claims = extractAllClaims(token, key);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration, SecretKey key) {
        // adiciona role no claim
        extraClaims.put("role", userDetails.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .orElse("USER"));

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }


    private boolean isTokenExpirado(String token, SecretKey key) {
        return extractExpiration(token, key).before(new Date());
    }

    private Date extractExpiration(String token, SecretKey key) {
        return extractClaim(token, Claims::getExpiration, key);
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getSignKeyRefresh() {
        byte[] keyBytes = Decoders.BASE64.decode(refreshSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}