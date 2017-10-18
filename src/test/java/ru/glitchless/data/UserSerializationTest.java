package ru.glitchless.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import ru.glitchless.data.mappers.LocalUserMapperToServerModel;
import ru.glitchless.data.mappers.Mapper;
import ru.glitchless.data.models.UserLocalModel;
import ru.glitchless.data.models.UserModel;

import java.io.IOException;

import static org.junit.Assert.*;

public class UserSerializationTest {
    private UserModel credentials;
    private Mapper<UserLocalModel, UserModel> mapper;

    @Before
    public void setup() {
        credentials = new UserModel("lionzxy", "123456789");
        credentials.setEmail("lol@lol.ru");
        mapper = new LocalUserMapperToServerModel();
    }

    @Test
    public void validMapper() throws IOException {
        testSerialization();
        testMapper();
    }

    private void testSerialization() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonUser = objectMapper.writeValueAsString(credentials);
        final UserModel user = objectMapper.readValue(jsonUser, UserModel.class);
        assertNotNull(user);
        assertEquals(credentials.getEmail(), user.getEmail());
        assertEquals(credentials.getLogin(), user.getLogin());
        assertNull("Пароль не должен выдаваться в json", user.getPassword());
    }

    private void testMapper() {
        final UserModel user = mapper.map(new UserLocalModel(
                1, credentials.getLogin(),
                credentials.getPassword(),
                credentials.getEmail()
        ));
        assertEquals(user.getLogin(), credentials.getLogin());
        assertEquals(user.getPassword(), credentials.getPassword());
        assertEquals(user.getEmail(), credentials.getEmail());
    }
}
