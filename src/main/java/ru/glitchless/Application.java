package ru.glitchless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.glitchless.di.PreInitConfiguration;
import ru.glitchless.di.WebsocketConfiguration;

@SpringBootApplication
public class Application {

    public static void main(String... args) {
        SpringApplication.run(new Class[]{PreInitConfiguration.class,
                WebsocketConfiguration.class,
                Application.class}, args);
    }
}
