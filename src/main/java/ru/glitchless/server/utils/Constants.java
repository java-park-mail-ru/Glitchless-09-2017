package ru.glitchless.server.utils;

import ru.glitchless.game.data.Point;

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
    public static final double GAME_ROTATION_COEFFICIENT = 360 / (Math.PI * 2);
    public static final Point GAME_FIELD_SIZE = new Point(1920, 1080);

    public static final double GAME_START_PLATFORM1 = Math.PI / 2;
    public static final double GAME_START_PLATFORM2 = Math.PI + Math.PI / 2;

    public static final int TPS = 20;
}
