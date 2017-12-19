package ru.glitchless.newserver.interactor.score;

import org.springframework.stereotype.Component;
import ru.glitchless.newserver.data.model.UserScore;
import ru.glitchless.newserver.repository.score.ScoreRepository;

import java.util.List;

@Component
public class ScoreInteractor {
    private final ScoreRepository scoreRepository;

    public ScoreInteractor(ScoreRepository repository) {
        this.scoreRepository = repository;
    }

    public void addScore(UserScore userScore) {
        scoreRepository.addScore(userScore);
    }

    public List<UserScore> getScores() {
        return scoreRepository.getScores();
    }
}
