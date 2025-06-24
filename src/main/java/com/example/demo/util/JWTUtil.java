package com.example.demo.util;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//
//@Component
//public class JWTUtil {
//    private final String SECRET="my-super-secret-key-that-is-long-enough-1234567890!@#";
//    private final SecretKey key= Keys.hmacShaKeyFor(SECRET.getBytes());
//    private final long EXPIRATION_TIME=1000*60*60;
//    public String generateToken(String username){
//        return Jwts.builder()
//                 .setSubject(username);
//                 .setIssuedAt(new Date());
//                 .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME));
//                 .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }
//}

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {
    //private final String SECRET = "secretkey";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "my-super-secure-jwt-secret-key-which-is-long-enough-123456".getBytes()
    );
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }
}
