package com.senai.engSecurity.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String SECRET_KEY = "yourverysecureandlongkeyofatleast32characters123456"; // Substitua por algo seguro
    private static final long EXPIRATION_TIME = 86400000; // 1 dia em milissegundos

    public String generateToken(String username) {
        logger.info("Generating token for user: {}", username);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        logger.info("Extracting username from token");
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
    try {
        logger.info("Validating token");
        Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token); // Verifica validade e assinatura
        return true;
    } catch (ExpiredJwtException e) {
        logger.error("Token expirado", e);
    } catch (JwtException | IllegalArgumentException e) {
        logger.error("Token inválido", e);
    }
    return false;
}

}
