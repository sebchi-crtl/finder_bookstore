package com.interview.bookstore.config.jwt;

import com.senxate.config.UserPrincipal;
import com.senxate.security.SecurityUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.senxate.constant.SecurityConstants.JWT_EXPIRATION_IN_MS;
import static com.senxate.constant.SecurityConstants.JWT_SECRET;

@Slf4j
@Component
public class JwtTokenProvider implements IJwtTokenProvider{


    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        Claims claims = extractClaims(request);
        if (claims.isEmpty() || claims == null)
            return null;
        String username = claims.getSubject();
        String userId = claims.get("user_id", String.class);

//        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
//                .map(SecurityUtils::convertToAuthority)
//                .collect(Collectors.toSet());

        UserDetails userDetails = User
                .withUsername(username)
                .roles("USER")
                .build();

        if (username == null)
            return null;

        return new UsernamePasswordAuthentictionToken(
                userDetails, claims
        );
    }

    @Override
    public boolean isTokenValid(HttpServletRequest request) {
        Claims claims = extractClaims(request);

        if (claims == null)
            return false;
        if (claims.getExpiration().before(new Date()))
            return false;

        return true;
    }

    @Override
    public boolean validateToken(String token) {
        try{

            return Jwts
                    .parserBuilder()
                    .setSigningKey(JWT_SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception ex) {
            return false;
        }
    }

    private boolean isTokenExpired(HttpServletRequest request) {
        return extractExpiration(request).before(new Date());
    }

    private Date extractExpiration(HttpServletRequest request) {
        return extractClaim(request, Claims::getExpiration);
    }

    private <T> T extractClaim(
            HttpServletRequest request,
            Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(request);
        if (claims == null)
            return null;
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(HttpServletRequest request) {
        String token = SecurityUtils.extractAuthTokenFromRequest(request);

        try {
            if (token == null)
                return null;

            Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

            return Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException exception) {
            log.error("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.error("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
        } catch (SignatureException exception) {
            log.error("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
        }
        return null;
    }
}
