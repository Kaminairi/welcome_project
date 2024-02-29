package com.laughbro.welcome.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class Jwt {
    private static String signKey="laughbro";
    public static Long expire=43200000L;

    public static String GenerateJwt(Map<String,Object> claims){
        String jwt=Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,signKey)
                .setExpiration(new Date(System.currentTimeMillis()+expire))
                .compact();
        return jwt;
    }
    public static Claims ParseJwt(String jwt){
        Claims claims=Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJwt(jwt)
                .getBody();
        return claims;
    }
}
