package com.watchly.Watchly.config;

import com.watchly.Watchly.model.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UsuarioEntity usuario){

        Date agora = new Date();

        Date validade = new Date(agora.getTime() + expiration);

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("role", usuario.getRole())
                .setIssuedAt(agora)
                .setExpiration(validade)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token){

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean isTokenValid(String token){

        try{

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration().after(new Date());

        }catch(Exception e){
            return false;
        }
    }
}