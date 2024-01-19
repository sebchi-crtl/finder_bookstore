package com.interview.bookstore.config.jwt;

import org.springframework.security.core.Authentication;


public interface IJwtTokenProvider {


    String generateJwtToken(Authentication response);

    String getUsernameFromToken(String token);

    boolean validateToken(String token);
}
