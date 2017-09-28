package ru.glitchless.utils;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/v1/**")
                .allowCredentials(true).allowedOrigins(
                "http://" + Constants.URL,
                "http://" + Constants.URL_HEROKU,
                "http://" + Constants.URL_DEBUG,
                "http://www." + Constants.URL,
                "http://www." + Constants.URL_HEROKU,
                "http://www." + Constants.URL_DEBUG,
                "https://" + Constants.URL,
                "https://" + Constants.URL_HEROKU,
                "https://" + Constants.URL_DEBUG,
                "https://www." + Constants.URL,
                "https://www." + Constants.URL_HEROKU,
                "https://www." + Constants.URL_DEBUG
        ).allowedMethods("GET", "HEAD", "POST", "PATCH", "DELETE");
    }
}
