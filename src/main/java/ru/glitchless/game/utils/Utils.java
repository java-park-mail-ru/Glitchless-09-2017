package ru.glitchless.game.utils;

import ru.glitchless.newserver.utils.Constants;

public class Utils {

    public static double degrees(double radians) {
        return radians * (Constants.CIRCLE_ANGEL / 2) / Math.PI + (Constants.CIRCLE_ANGEL / 2);
    }

    public static double radians(double degrees) {
        return degrees * (Constants.CIRCLE_ANGEL / Math.PI * 2);
    }
}
