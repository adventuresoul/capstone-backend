package com.example.demo.controller;

import com.example.demo.entity.AuthRequest;
import com.example.demo.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;

import java.util.Collections;

//@RestController
//public class AuthController {
//    public String generateToken(@RequestBody AuthRequest authRequest){
//
//        @Autowired
//        AuthenticationManager authenticationManager;
//
//        @Autowired
//        JWTUtil jwtUtil;
//
//        @PostMapping("/authenticate")
//        public String generateToken(@RequestBody AuthRequest authRequest){
//            try {
//                authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
//            );
//                return jwtUtil.generateToken(authRequest.getUsername());
//            }catch(Exception e){
//                throw e;
//            }
//        }
//    }
//}
//@RestController
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JWTUtil jwtUtil;
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//            );
//            String token = jwtUtil.generateToken(authRequest.getUsername());
//            return ResponseEntity.ok(token);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//    }
//}
@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        String token = jwtUtil.generateToken(authRequest.getUsername());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}