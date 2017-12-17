package ru.glitchless.newserver.data.model;

public class UserScore {
    private final String user;
    private final long score;

    public UserScore(String user, long score) {
        this.user = user;
        this.score = score;
    }

    public String getUser() {
        return user;
    }

    public long getScore() {
        return score;
    }
}
