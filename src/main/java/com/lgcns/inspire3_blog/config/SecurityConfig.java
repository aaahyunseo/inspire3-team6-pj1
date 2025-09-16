package com.lgcns.inspire3_blog.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 추가
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/swagger-ui/**",
                    "/api/v1/users/signup",
                    "/api/v1/users/signin",
                    "/api/v1/users/logout",
                    "/api/v1/users/**",  // 추후에 연결시 제거해야됌 권환 필요
                    "/api/v1/fortune/**",  // 추후에 연결시 제거해야됌 권환 필요
                    "/api/v1/summary/**",
                    "/api/v1/weather/short-term/info",
                    "/api/v1/comments/*/list",
                    "/api/v1/todos/**",  // main branch merge 전 제거
                    "/api/v1/rank/**",
                    "/v3/api-docs/**", // main branch merge 전 제거
                    "/api/v1/boards/**",  // main branch merge 전 제거
                    "/api/v1/comments/**",  // main branch merge 전 제거
                    "/api/v1/mypage/**"  // main branch merge 전 제거

                ).permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}