package ru.glitchless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.glitchless.utils.Constants;

@SpringBootApplication
public class Application {
    public static void main(String... args) {
        final ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);
        app.registerShutdownHook();
    }

    @Bean
    @SuppressWarnings("AnonymousInnerClassMayBeStatic")
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/v1/**")
                        .allowCredentials(true).allowedOrigins(
                        "http://" + Constants.URL,
                        "http://" + Constants.URL_HEROKU,
                        "http://www." + Constants.URL,
                        "http://www." + Constants.URL_HEROKU,
                        "https://" + Constants.URL,
                        "https://" + Constants.URL_HEROKU,
                        "https://www." + Constants.URL,
                        "https://www." + Constants.URL_HEROKU
                ).allowedMethods("GET", "HEAD", "POST", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }
}
