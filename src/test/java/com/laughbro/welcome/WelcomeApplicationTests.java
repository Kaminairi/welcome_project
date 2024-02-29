package com.laughbro.welcome;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class WelcomeApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public  void test(){
		Map<String,Object> claims=new HashMap<>();
		claims.put("id","111111");
		claims.put("pwd","123456");
		String jwt= Jwts.builder()
				.addClaims(claims)
				.signWith(SignatureAlgorithm.HS256,"laughbro")
				.setExpiration(new Date(System.currentTimeMillis()+43200000L))
				.compact();
		System.out.println(jwt);
	}

}
