package ru.glitchless.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.glitchless.data.models.UserModel;
import ru.glitchless.data.stores.UserDao;
import ru.glitchless.data.throwables.UserAlreadyExist;
import ru.glitchless.utils.RandomString;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserDaoTest {
    private final Random random = new Random();
    @Autowired
    private UserDao userDao;

    @Test
    public void testDao() {
        setAndGetCheckTest(new UserModel("123456789", "lmsdfpmsd;fm[psdmf"));

        UserModel tmpUser = new UserModel("edsfsdvsdv", "sdfsdcsddcs");
        tmpUser.setEmail("nisdfcsd");
        setAndGetCheckTest(tmpUser);
        citextValueTest(tmpUser);

        tmpUser = new UserModel(new RandomString().nextString(), "sdfsdcsddcs");
        tmpUser.setEmail("123456789012345678901234567890");
        setAndGetCheckTest(tmpUser);
        citextValueTest(tmpUser);
    }

    private void setAndGetCheckTest(UserModel model) {
        userDao.clearAllTable();
        userDao.addUser(model);
        final UserModel fromBD = userDao.getUser(model.getLogin());
        assertEquals(fromBD.getLogin(), model.getLogin());
        assertEquals(fromBD.getEmail(), model.getEmail());
        assertEquals(fromBD.getPassword(), model.getPassword());
    }

    private void citextValueTest(UserModel model) {
        userDao.clearAllTable();
        UserModel tmpModel = new UserModel(randomizedString(model.getLogin()), model.getPassword());
        userDao.addUser(tmpModel);

        assertEquals(userDao.getUser(model.getLogin()).getPassword(), model.getPassword());
        boolean checkLogin = false;
        try {
            userDao.addUser(model);
        } catch (RuntimeException e) {
            assertTrue("Ожидалось ошибка что пользователь уже существует", e instanceof UserAlreadyExist);
            checkLogin = true;
        }

        assertTrue("Удалось добавить пользователя с одинаковым логином", checkLogin);
        userDao.clearAllTable();
        tmpModel.setEmail(randomizedString(model.getEmail()));
        userDao.addUser(tmpModel);

        tmpModel = new UserModel(tmpModel.getLogin() + "_test", tmpModel.getPassword());
        tmpModel.setEmail(model.getEmail());

        try {
            userDao.addUser(tmpModel);
        } catch (RuntimeException e) {
            assertTrue("Ожидалось ошибка что почта уже существует", e instanceof UserAlreadyExist);
            return;
        }

    }

    private String randomizedString(String source) {
        final char[] tmpArray = source.toCharArray();
        for (int i = 0; i < tmpArray.length; i++) {
            if (random.nextBoolean()) {
                tmpArray[i] = Character.toUpperCase(tmpArray[i]);
            } else {
                tmpArray[i] = Character.toLowerCase(tmpArray[i]);
            }
        }
        return String.valueOf(tmpArray);
    }
}
