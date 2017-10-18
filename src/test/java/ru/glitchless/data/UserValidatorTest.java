package ru.glitchless.data;

import org.junit.Before;
import org.junit.Test;
import ru.glitchless.data.models.UserModel;
import ru.glitchless.data.throwables.InvalidLoginOrPassword;
import ru.glitchless.repositories.auth.validators.UserValidator;
import ru.glitchless.utils.RandomString;

import static org.junit.Assert.assertTrue;

public class UserValidatorTest {
    private UserModel user;
    private UserValidator validator;

    @Before
    public void setup() {
        user = new UserModel("lionzxy", "123456789");
        user.setEmail("lol@lol.ru");
        validator = new UserValidator();
    }

    @Test
    public void testEmailValidator() {
        validEmailTest("nikita@kulikof.ru");
        validEmailTest("nikita.asdsad@gmail.com");
        validEmailTest("nikita.asdsad@corp.mail.ru");
        validEmailTest(null);
        validEmailTest("");
        notValidEmailTest("1`234567890");
        notValidEmailTest("@mail.ru");
        notValidEmailTest("mail.ru");
        notValidEmailTest("%^&*&^@&mail.ru");
        notValidEmailTest("%^&*&^@&$%^.ru");
    }

    private void notValidEmailTest(String email) {
        final UserModel model = new UserModel("lionzxy", "123456789");
        model.setEmail(email);
        try {
            validator.validate(model);
        } catch (InvalidLoginOrPassword ex) {
            assertTrue("Неправильная ошибка на " + email, ex.getMessage().equals("Invalid email!"));
            return;
        }
        assertTrue("Не выдало ошибку на " + email, false);
    }

    private void validEmailTest(String email) {
        final UserModel model = new UserModel("lionzxy", "123456789");
        model.setEmail(email);
        try {
            validator.validate(user);
        } catch (InvalidLoginOrPassword ex) {
            assertTrue("Выдало ошибку на " + email, false);
            return;
        }
        assertTrue(true);
    }

    @Test
    public void testUserValidator() {
        validLoginTest("Lionzxy");
        validLoginTest("LionZXY");
        validLoginTest("Nagibator123456789");
        notValidLoginTest("ilsufhp9vjj498j309m-12-=101`-[p13ea21324rg3evfd@!");
        notValidLoginTest(null);
        notValidLoginTest("");
        notValidLoginTest("L0XP*dor");
    }

    private void notValidLoginTest(String login) {
        final UserModel model = new UserModel(login, "123456789");
        try {
            validator.validate(model);
        } catch (InvalidLoginOrPassword ex) {
            assertTrue("Неправильная ошибка на " + login, ex.getMessage().equals("Login can't be empty")
                    || ex.getMessage().equals("Invalid symblos! Login can be contains only [A-Za-z0-9]"));
            return;
        }
        assertTrue("Не выдало ошибку на " + login, false);
    }

    private void validLoginTest(String login) {
        final UserModel model = new UserModel(login, "123456789");
        try {
            validator.validate(model);
        } catch (InvalidLoginOrPassword ex) {
            assertTrue("Выдало ошибку на " + login, false);
            return;
        }
        assertTrue(true);
    }

    @Test
    public void testPasswordValidator() {
        validPasswordTest("1234567890");
        validPasswordTest("qwertyui");
        RandomString randomString = new RandomString();
        for (int i = 0; i < 1000; i++) {
            validPasswordTest(randomString.nextString());
        }
        notValidPasswordTest(null);
        notValidPasswordTest("");
    }

    private void validPasswordTest(String password) {
        final UserModel model = new UserModel("lionzxy", password);
        try {
            validator.validate(model);
        } catch (InvalidLoginOrPassword e) {
            assertTrue("Не прошла проверку на пароль " + password, false);
            return;
        }
        assertTrue(true);
    }

    private void notValidPasswordTest(String password) {
        final UserModel model = new UserModel("lionzxy", password);
        try {
            validator.validate(model);
        } catch (InvalidLoginOrPassword e) {
            assertTrue("Неправильная ошибка на " + password, e.getMessage().equals("Password can't be null"));
            return;
        }
        assertTrue(true);
    }
}