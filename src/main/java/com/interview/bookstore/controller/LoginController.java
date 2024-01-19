package com.interview.bookstore.controller;

import com.interview.bookstore.constant.BookConstant;
import com.interview.bookstore.dto.LoginRequestDTO;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoginController {

    @Value("${app.jwt.my-key}")
    private String JWT_SECRET;
    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;
    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            // Create an authentication token with the provided username and password
            Authentication authenticationRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());

            Authentication authenticationResponse =
                    this.authenticationManager.authenticate(authenticationRequest);

            System.out.println(authenticationResponse);
            String jwtToken = generateJwtToken(authenticationResponse);

            // Return the JWT token in the response
            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            // Handle authentication failure
            return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
        }

    }

    private String generateJwtToken(Authentication response) {
        UserDetails userDetails = (UserDetails) response.getPrincipal();

        String jwtToken = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", "USER")
                .claim("name", response.getName())
                .claim("credentials", response.getCredentials())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS)) //10 days
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
        return jwtToken;
    }


}
