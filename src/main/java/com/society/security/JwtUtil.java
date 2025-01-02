//package com.society.security;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.crypto.SecretKey;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//
//@Component
//public class JwtUtil {
//
//    private final String SECRET_KEY;
//    private final TokenBlacklistService blacklistService;
//
//    @Autowired
//    public JwtUtil(JwtConfig jwtConfig, TokenBlacklistService blacklistService) {
//        this.SECRET_KEY = jwtConfig.getSecret();  // Ensure jwtConfig.getSecret() returns a non-null value
//        this.blacklistService = blacklistService;
//    }
//
//    // Method to generate the signing key
//    private SecretKey getSigningKey() {
//        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//    }
//
//    // Extract the username (subject) from the token
//    public String extractUsername(String token) {
//        return extractAllClaims(token).getSubject();
//    }
//
//    // Extract the expiration date from the token
//    public Date extractExpiration(String token) {
//        return extractAllClaims(token).getExpiration();
//    }
//
//    // Extract all claims from the token
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser()
//                .verifyWith(getSigningKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//
//    // Check if the token is expired
//    private Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    // Generate a token with username and role
//    public String generateToken(String username) {
////        Map<String, Object> claims = new HashMap<>();
////        claims.put("role", role);  // Store the user's role in the token
////        return createToken(claims, username);
//    	  Map<String, Object> claims = new HashMap<>();
//          return createToken(claims, username);
//    }
//
//    // Create the JWT token with claims and subject (username)
//    private String createToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .claims(claims)
//                .subject(subject)
//                .header().empty().add("typ","JWT")
//                .and()
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 5 minutes expiration time
//                .signWith(getSigningKey())
//                .compact();
//    }
// 
//    // Validate the token (check if it's blacklisted or expired)
//    public Boolean validateToken(String token) {
//        if (blacklistService.isTokenBlacklisted(token)) {
//            return false;  // If the token is blacklisted, it is invalid
//        }
//        return !isTokenExpired(token);  // Return true if the token is not expired
//    }
//
//    // Blacklist the token (for example, during logout)
//    public void blacklistToken(String token) {
//        Claims claims = extractAllClaims(token);
//        blacklistService.blacklistToken(token);  // Store the token in the blacklist
//    }
//}

package com.society.security;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final String SECRET_KEY;

	private final TokenBlacklistService blacklistService;


    @Autowired
    public JwtUtil(JwtConfig jwtConfig) {
        this.SECRET_KEY = jwtConfig.getSecret();
		this.blacklistService = new TokenBlacklistService();
      
        // Check if the secret is loaded correctly
        if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
            throw new IllegalArgumentException("JWT Secret Key is not configured");
        }
    }
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	public String extractUsername(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}

	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(String username,String role) {
		Map<String, Object> claims = new HashMap<>();
		
		claims.put("role", role);
		//createToken(claims, username));
		
		return createToken(claims, username);
	}

	private String createToken(Map<String, Object> claims, String subject) {
		
		return Jwts.builder().claims(claims).subject(subject).header().empty().add("typ", "jwt").and()
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)).signWith(getSigningKey()).compact();
	}

	public Boolean validateToken(String token) {
		if (blacklistService.isTokenBlacklisted(token)) {
			return false; // Token is invalid if it’s blacklisted
		}
		return !isTokenExpired(token);
	}

	public void blacklistToken(String token) {
		Claims claims = extractAllClaims(token);
		long expirationTime = claims.getExpiration().getTime();
		blacklistService.blacklistToken(token);
	}

}