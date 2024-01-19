package com.interview.bookstore.config;


import com.interview.bookstore.constant.BookConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    private final BookConstant constant;

    public CorsConfig(BookConstant constant) {
        this.constant = constant;
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(constant.allowedOrigins);
        configuration.setAllowedMethods(constant.allowedMethods);
        configuration.setAllowedHeaders(constant.allowedHeaders);
        configuration.setExposedHeaders(constant.expectedHeaders);
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(constant.MAX_AGE_SECS);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
