package ru.glitchless.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.glitchless.utils.PropertiesFile;

@Service
public class UserService {

    @Autowired
    PropertiesFile propertiesFile;
}
