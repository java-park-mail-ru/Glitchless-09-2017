package ru.glitchless.newserver.utils;

import ru.glitchless.game.data.Point;

import java.util.concurrent.TimeUnit;

public class Constants {
    public static final String[] TRUSTED_URLS = new String[]{
            "https://glitchless.herokuapp.com",
            "https://glitchless.ru",
            "http://localhost:8080",
    };
    public static final int MAGIC_NUMBER = 31;
    public static final String SESSION_EXTRA_USER = "user";
    public static final String SESSION_EXTRA_USERWS = "userws";


    public static final int MAX_ROOMS_COUNT = 2;
    public static final int CIRCLE_RADIUS = 1080 / 2 - 60;
    public static final float GAME_ROTATION_COEFFICIENT = (float) (360f / (Math.PI * 2));
    public static final Point GAME_FIELD_SIZE = new Point(1920, 1080);

    public static final float GAME_START_PLATFORM1 = 90;
    public static final float GAME_START_PLATFORM2 = 240;
    public static final float GAME_RADIUS_PLATFORM_PADDING = 105f / 4;

    public static final int TPS = 20;
    public static final long STEP_TIME = TimeUnit.SECONDS.toMillis(1) / Constants.TPS;
}
