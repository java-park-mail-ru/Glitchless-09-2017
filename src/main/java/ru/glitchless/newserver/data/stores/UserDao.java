package ru.glitchless.newserver.data.stores;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.glitchless.newserver.data.model.UserModel;
import ru.glitchless.server.data.throwables.UserAlreadyExist;
import ru.glitchless.server.data.throwables.UserNotFound;

@Service
@Transactional
public class UserDao {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    private static final RowMapper<UserModel> USER_MAPPER = (rs, num) ->
            new UserModel(
                    rs.getInt(COLUMN_ID),
                    rs.getString(COLUMN_LOGIN),
                    rs.getString(COLUMN_PASSWORD),
                    rs.getString(COLUMN_EMAIL));

    private final JdbcTemplate template;

    public UserDao(JdbcTemplate template) {
        this.template = template;
    }

    public UserModel addUser(UserModel userLocalModel) {
        try {
            return template.queryForObject("INSERT INTO users (login, password, email) VALUES (?, ?, ?) RETURNING *;",
                    USER_MAPPER,
                    userLocalModel.getLogin(),
                    userLocalModel.getPassword(),
                    userLocalModel.getEmail());
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExist();
        }
    }

    public UserModel getUser(String login) {
        try {
            return template.queryForObject("SELECT * FROM users WHERE login = ?::CITEXT;",
                    USER_MAPPER,
                    login);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFound();
        }
    }

    public UserModel updateUser(String login, String email) {
        return template.queryForObject("UPDATE users SET email = ? WHERE login = ?::CITEXT RETURNING *",
                USER_MAPPER,
                email,
                login);

    }

    public void clearAllTable() {
        template.update("DELETE FROM users;");
    }
}
