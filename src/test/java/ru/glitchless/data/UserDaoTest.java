package ru.glitchless.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.glitchless.Application;
import ru.glitchless.data.models.UserLocalModel;
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
    @Autowired
    private UserDao userDao;
    private final Random random = new Random();

    @Test
    public void testDao() {
        setAndGetCheckTest(new UserLocalModel("123456789", "lmsdfpmsd;fm[psdmf"));

        UserLocalModel tmpUser = new UserLocalModel("edsfsdvsdv", "sdfsdcsddcs");
        tmpUser.setEmail("nisdfcsd");
        setAndGetCheckTest(tmpUser);
        citextValueTest(tmpUser);

        tmpUser = new UserLocalModel(new RandomString().nextString(), "sdfsdcsddcs");
        tmpUser.setEmail("123456789012345678901234567890");
        setAndGetCheckTest(tmpUser);
        citextValueTest(tmpUser);
    }

    private void setAndGetCheckTest(UserLocalModel model) {
        userDao.clearAllTable();
        userDao.addUser(model);
        final UserLocalModel fromBD = userDao.getUser(model.getLogin());
        assertEquals(fromBD.getLogin(), model.getLogin());
        assertEquals(fromBD.getEmail(), model.getEmail());
        assertEquals(fromBD.getPasswordBCrypt(), model.getPasswordBCrypt());
    }

    private void citextValueTest(UserLocalModel model) {
        userDao.clearAllTable();
        UserLocalModel tmpModel = new UserLocalModel(randomizedString(model.getLogin()), model.getPasswordBCrypt());
        userDao.addUser(tmpModel);

        assertEquals(userDao.getUser(model.getLogin()).getPasswordBCrypt(), model.getPasswordBCrypt());
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

        tmpModel = new UserLocalModel(tmpModel.getLogin() + "_test", tmpModel.getPasswordBCrypt());
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
