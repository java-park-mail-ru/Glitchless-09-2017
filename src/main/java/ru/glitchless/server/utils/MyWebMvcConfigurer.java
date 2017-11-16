package ru.glitchless.server.utils;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class MyWebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true).allowedOrigins(Constants.TRUSTED_URLS)
                .allowedMethods("GET", "HEAD", "POST", "PATCH", "DELETE", "OPTIONS");
    }
}