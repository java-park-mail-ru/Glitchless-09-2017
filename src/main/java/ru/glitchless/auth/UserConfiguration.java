package ru.glitchless.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.glitchless.utils.PropertiesFile;

@Configuration
public class UserConfiguration {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    PropertiesFile getProperties(){
        return new PropertiesFile();
    }
}
