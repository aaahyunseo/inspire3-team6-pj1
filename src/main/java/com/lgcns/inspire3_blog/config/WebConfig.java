package com.lgcns.inspire3_blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedOriginPatterns("http://localhost:3000")
                .allowedMethods("*")
                .allowCredentials(true)
                .exposedHeaders("Authorization", "Refresh-Token");
    }
}
