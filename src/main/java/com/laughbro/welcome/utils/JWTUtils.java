package com.laughbro.welcome.utils;

import java.util.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
public class JWTUtils {
    /**
     * 密钥要⾃⼰保管好
     */
    private static String SECRET = "!RHO4$%*^fi$R";
    /**
     * 传⼊payload信息获取token
     * @param map payload
     * @return token
     */
    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();
//payload
        map.forEach(builder::withClaim);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 3); //默认3天过期
        builder.withExpiresAt(instance.getTime());//指定令牌的过期时间
        return builder.sign(Algorithm.HMAC256(SECRET));
    }

    public String buildToken(String id,String name){
        Map<String, Object> map = new HashMap<>();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 2000);

        String token = JWT.create().withHeader(map) //header
                .withClaim("userId", id)//payload
                .withClaim("username", name)//payload
                .withExpiresAt(instance.getTime())//指定令牌的过期时间
                .sign(Algorithm.HMAC256(SECRET)); //签名，密钥⾃⼰记住
        return token;
    }


    /**
     * 验证token
     */
    public static DecodedJWT verify(String token) {
//如果有任何验证异常，此处都会抛出异常
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }
    /**
     * 获取token中的payload
     */
    public static Map<String, Claim> getPayloadFromToken(String token) {
        return
                JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token).getClaims();
    }

}
