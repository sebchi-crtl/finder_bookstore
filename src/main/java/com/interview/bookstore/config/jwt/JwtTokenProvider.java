package com.interview.bookstore.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JwtTokenProvider {

    private static String JWT_SECRET = "432646294A404D635166546A576E5A7234753778214125442A472D4B6150645267556B58703273357638792F423F4528482B4D6251655468566D59713374367";
//    @Value("${app.jwt.expiration-in-ms}")
    private static Long JWT_EXPIRATION_IN_MS = 864000000L;

    public static String generateJwtToken(String username, List<GrantedAuthority> authorities) {
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS)) //10 days
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        log.info("received to getAuthentication");

        String token = request.getHeader("Authorization");

        if (token != null){
            String user = Jwts
                    .parserBuilder()
                    .setSigningKey(JWT_SECRET)
                    .build()
                    .parseClaimsJws(token.replace(
                            "Bearer ", ""))
                    .getBody()
                    .getSubject();
            log.info("token cleared");
            if (user != null){
                List<GrantedAuthority> authorities = (List<GrantedAuthority>) Jwts
                        .parserBuilder()
                        .setSigningKey(JWT_SECRET)
                        .build()
                        .parseClaimsJws(token.replace(
                                "Bearer ", ""
                        ))
                        .getBody()
                        .get("roles", List.class)
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());
                log.info("user cleared");
                log.info(authorities.toString());
                return new UsernamePasswordAuthenticationToken(
                        user, null, authorities
                );
            }
            return null;
        }
        return null;
    }



}
