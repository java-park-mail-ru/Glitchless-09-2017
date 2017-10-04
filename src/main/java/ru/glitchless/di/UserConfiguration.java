package ru.glitchless.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.glitchless.data.mappers.LocalUserMapperToServerModel;
import ru.glitchless.data.mappers.Mapper;
import ru.glitchless.data.models.UserLocalModel;
import ru.glitchless.data.models.UserModel;
import ru.glitchless.repositories.auth.validators.UserValidator;

@Configuration
public class UserConfiguration {

    @Bean
    UserValidator getUserValidator() {
        return new UserValidator();
    }

    @Bean
    Mapper<UserLocalModel, UserModel> getUserMapper() {
        return new LocalUserMapperToServerModel();
    }
}
