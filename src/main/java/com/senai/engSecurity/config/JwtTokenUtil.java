package com.senai.engSecurity.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final String SECRET_KEY = "senha"; // Substitua por algo seguro
    private static final long EXPIRATION_TIME = 86400000; // 1 dia em milissegundos

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
    try {
        Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token); // Verifica validade e assinatura
        return true;
    } catch (ExpiredJwtException e) {
        System.out.println("Token expirado");
    } catch (JwtException | IllegalArgumentException e) {
        System.out.println("Token inválido");
    }
    return false;
}

}
