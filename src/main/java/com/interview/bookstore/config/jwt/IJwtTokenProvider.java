package com.interview.bookstore.config.jwt;

import com.senxate.config.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;


public interface IJwtTokenProvider {


    Authentication getAuthentication(HttpServletRequest request);

    boolean isTokenValid(HttpServletRequest request);

    boolean validateToken(String token);
}
