package ru.glitchless.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.glitchless.utils.IPropertiesFile;

@Service
public class UserService {

    @Autowired
    IPropertiesFile propertiesFile;
}
