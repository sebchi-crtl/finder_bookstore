package com.interview.bookstore.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager manager){
        super(manager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws IOException,
            ServletException
    {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")){
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken token =
                JwtTokenProvider.getAuthentication(request);
        log.info("passed to getAuthentication");
//        Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTcwNTY5NTAyMywiZXhwIjoxNzA2NTU5MDIzfQ.z0GHjZ1HDlPxUKxhw626iFeOGDUVFMl4km_Mla6milcbk7XGdBMmgkO7WhinUOglqj5h9RgvgmmRgof1NTB6WA
        SecurityContextHolder.getContext().setAuthentication(
                token
        );
        chain.doFilter(request, response);
    }
}
