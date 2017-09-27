package ru.glitchless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String... args) {
        final ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);
        app.registerShutdownHook();
    }
}
