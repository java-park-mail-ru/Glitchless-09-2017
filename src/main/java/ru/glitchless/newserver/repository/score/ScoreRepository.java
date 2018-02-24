package ru.glitchless.newserver.repository.score;

import org.springframework.stereotype.Component;
import ru.glitchless.newserver.data.dao.ScoreDao;
import ru.glitchless.newserver.data.model.UserScore;

import java.util.List;

@Component
public class ScoreRepository {
    private final ScoreDao scoreDao;

    public ScoreRepository(ScoreDao dao) {
        this.scoreDao = dao;
    }

    public void addScore(UserScore userScore) {
        scoreDao.addScore(userScore);
    }

    public List<UserScore> getScores() {
        return scoreDao.getScores();
    }
}
