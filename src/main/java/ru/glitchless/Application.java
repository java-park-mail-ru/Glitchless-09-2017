package ru.glitchless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class Application {
    public static void main(String... args) {
        ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);
    }
}
