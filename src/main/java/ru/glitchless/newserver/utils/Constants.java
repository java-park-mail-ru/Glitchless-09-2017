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
    public static final int REFERENCE_LENGHT = 20;
    public static final long PING_TIMEOUT = 10000L;
    public static final String SESSION_EXTRA_USER = "user";
    public static final String SESSION_EXTRA_USERWS = "userws";
    public static final int MESSAGE_THREAD_COUNT = 20;


    public static final int MAX_ROOMS_COUNT = 2;
    public static final int CIRCLE_RADIUS = 1080 / 2 - 60;
    public static final float CIRCLE_ANGEL = 360f;
    public static final float SHIELD_REGEN_RATIO = (float) 5 / 1000;
    public static final float SHIELD_ACTIVATION_PERCENT = 0.1f;
    public static final float LASER_DAMAGE = 20;
    public static final float MAX_SHIELD = 100;
    public static final long LASER_PER_TIME = 2000;
    public static final double FORCEFIELD_ANGEL_COEFICIENT = 1.1;
    public static final float GAME_ROTATION_COEFFICIENT = (float) (360f / (Math.PI * 2));
    public static final Point GAME_FIELD_SIZE = new Point(1920, 1080);
    public static final float GAME_PLATFORM_SIZE = 105;
    public static final float GAME_ALIEN_SIZE = 100;
    public static final int GAME_HP_COUNT = 5;
    public static final float GAME_HP_CIRCLE_RADIUS = (float) ((1080 / 2 - 60) * 1.15);
    public static final float GAME_FORCEFIELD_RADIUS = (float) ((1080 / 2 - 60) * 1.1);

    public static final float GAME_START_PLATFORM1 = 90;
    public static final float GAME_START_PLATFORM2 = 270;
    public static final float GAME_RADIUS_PLATFORM_PADDING = 105f / 4;

    public static final int TPS = 20;
    public static final long STEP_TIME = TimeUnit.SECONDS.toMillis(1) / Constants.TPS;
}
