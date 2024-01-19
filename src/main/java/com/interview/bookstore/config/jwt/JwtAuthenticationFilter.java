package com.interview.bookstore.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.bookstore.dto.AuthenticationRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager manager;

    public JwtAuthenticationFilter(AuthenticationManager manager) {
        this.manager = manager;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException {
        try {
            AuthenticationRequest authenticationRequest =
                    new ObjectMapper().readValue(
                            request.getInputStream(),
                            AuthenticationRequest.class
                    );
            Authentication authentication = new
                    UsernamePasswordAuthenticationToken(
                    authenticationRequest.username(),
                    authenticationRequest.password(),
                    new ArrayList<>()
            );
            return manager.authenticate(authentication);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        String token = JwtTokenProvider.generateJwtToken(authResult.getName(), (List<GrantedAuthority>) authResult.getAuthorities());
        response.addHeader("Authorization", "Bearer " + token);
        System.out.println(token);
    }


}
