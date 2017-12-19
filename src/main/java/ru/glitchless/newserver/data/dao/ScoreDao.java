package ru.glitchless.newserver.data.dao;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.glitchless.newserver.data.model.UserScore;
import ru.glitchless.newserver.data.throwables.UserNotFound;

import java.util.Collections;
import java.util.List;

@Component
public class ScoreDao {
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_SCORE = "score";

    private static final RowMapper<UserScore> SCORE_MAPPER = (rs, num) ->
            new UserScore(
                    rs.getString(COLUMN_LOGIN),
                    rs.getLong(COLUMN_SCORE));

    private final JdbcTemplate template;

    public ScoreDao(JdbcTemplate template) {
        this.template = template;
    }

    public void addScore(UserScore userScore) {
        try {
            template.update("INSERT INTO score (score, userid)\n"
                    + "  SELECT\n"
                    + "    ?,\n"
                    + "    usr.id\n"
                    + "  FROM users AS usr\n"
                    + "  WHERE usr.login = ?::CITEXT;", userScore.getScore(), userScore.getUser());
        } catch (DataIntegrityViolationException e) {
            throw new UserNotFound();
        }
    }

    public List<UserScore> getScores() {
        try {
            return template.query("SELECT users.login AS login, score.score AS score "
                            + "FROM score JOIN users ON score.userid = users.id ORDER BY score.score DESC;",
                    SCORE_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }
}
