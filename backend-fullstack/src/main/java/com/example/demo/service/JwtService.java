package com.example.demo.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
	public String gettoken(String Username) {
		
		HashMap<String, Object> cliams = new HashMap<>();
		
		
		return generateToken(Username,cliams);
	}

	
	/////////////this is actually the claimsm resolver
	public String generateToken(String username, HashMap<String, Object> cliams) {
		// TODO Auto-generated method stub
		return Jwts.builder()
				.setClaims(cliams)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
				.signWith(getSignKey(),SignatureAlgorithm.HS256)
				.compact();
	}

	private Key getSignKey() {
		// TODO Auto-generated method stub
		String Secret = "743677397A244326452948404D635166546A576E5A7234753778214125442A47";
		byte[] keyy = Decoders.BASE64.decode(Secret);
		return Keys.hmacShaKeyFor(keyy);
	}
	
	public String GetUsername(String token) {
//		
//		Claims data = extractAllClaims(token);
//		
//		return data.getSubject();
		String info = extractclaims(token, Claims::getSubject);
		
		return info;
		
		
		
		
	}
	public Date getExpirationDate(String token) {
//		Claims data = extractAllClaims(token);
//		
//		Date info = data.getExpiration();
//		return false;
		Date date = extractclaims(token, Claims::getExpiration);
		return date;
		
	}
	
	
	private Claims extractAllClaims(String token) {
		
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		
	}
	
	public<T> T extractclaims(String token,Function<Claims,T> claimsResolver) {
		final Claims data = extractAllClaims(token);
		
		return claimsResolver.apply(data);
	}
}
