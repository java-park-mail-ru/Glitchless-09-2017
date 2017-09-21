package ru.glitchless.auth;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.glitchless.auth.validators.IPasswordValidator;
import ru.glitchless.auth.validators.IUserValidator;
import ru.glitchless.auth.validators.PasswordValidator;
import ru.glitchless.auth.validators.UserValidator;
import ru.glitchless.utils.IPropertiesFile;
import ru.glitchless.utils.PropertiesFile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class UserConfiguration {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    ExecutorService getService() {
        return Executors.newCachedThreadPool();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    IPropertiesFile getProperties(ExecutorService service) {
        return new PropertiesFile(service);
    }


    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    IPasswordValidator getPasswordValidator() {
        return new PasswordValidator();
    }


    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    IUserValidator getUserValidator(IPasswordValidator validator) {
        return new UserValidator(validator);
    }

}
