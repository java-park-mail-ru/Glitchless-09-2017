package ru.glitchless.data.stores;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.glitchless.data.models.UserLocalModel;
import ru.glitchless.data.throwables.UserAlreadyExist;
import ru.glitchless.data.throwables.UserNotFound;

@Service
@Transactional
public class UserDao {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    private static final RowMapper<UserLocalModel> USER_MAPPER = (rs, num) ->
            new UserLocalModel(
                    rs.getInt(COLUMN_ID),
                    rs.getString(COLUMN_LOGIN),
                    rs.getString(COLUMN_PASSWORD),
                    rs.getString(COLUMN_EMAIL));

    private final JdbcTemplate template;

    public UserDao(JdbcTemplate template) {
        this.template = template;
    }

    public UserLocalModel addUser(UserLocalModel userLocalModel) {
        try {
            return template.queryForObject("INSERT INTO users (login, password, email) VALUES (?, ?, ?) RETURNING *;",
                    USER_MAPPER,
                    userLocalModel.getLogin(),
                    userLocalModel.getPasswordBCrypt(),
                    userLocalModel.getEmail());
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExist();
        }
    }

    public UserLocalModel getUser(String login) {
        try {
            return template.queryForObject("SELECT * FROM users WHERE login = ?::CITEXT;",
                    USER_MAPPER,
                    login);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFound();
        }
    }

    public void clearAllTable() {
        template.update("DELETE FROM users;");
    }
}
