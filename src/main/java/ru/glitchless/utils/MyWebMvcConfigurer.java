package ru.glitchless.utils;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;

public class MyWebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        final ArrayList<String> urls = new ArrayList<>();
        for (String url : Constants.TRUSTED_URLS) {
            urls.add("http://" + url);
            urls.add("http://www." + url);
            urls.add("https://" + url);
            urls.add("https://www." + url);
        }
        registry.addMapping("/**")
                .allowCredentials(true).allowedOrigins(urls.toArray(new String[urls.size()]))
                .allowedMethods("GET", "HEAD", "POST", "PATCH", "DELETE", "OPTIONS");
    }
}