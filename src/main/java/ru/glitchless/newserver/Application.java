package ru.glitchless.newserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.glitchless.newserver.di.PreInitConfiguration;
import ru.glitchless.newserver.di.WebsocketConfiguration;

@SpringBootApplication
public class Application {

    public static void main(String... args) {
        SpringApplication.run(new Class[]{PreInitConfiguration.class,
                WebsocketConfiguration.class,
                Application.class}, args);
    }
}
