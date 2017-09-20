package ru.glitchless.auth;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.glitchless.utils.IPropertiesFile;
import ru.glitchless.utils.PropertiesFile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class UserConfiguration {
    private ExecutorService service = Executors.newCachedThreadPool();

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    IPropertiesFile getProperties() {
        return new PropertiesFile(service);
    }
}
