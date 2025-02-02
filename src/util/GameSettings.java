package util;

import enums.DifficultyLevel;

import java.awt.*;

public class GameSettings {
    private static GameSettings instance;
    public static int simulationInterval = 1000;
    public static int windowWidth = 1366;
    public static int windowHeight = 768;
    public static int sidebarWidht = 300;
    public static int mapWidth = windowWidth - sidebarWidht;
    private static DifficultyLevel difficultyLevel = DifficultyLevel.EASY;
    public static Color mainBackgroundGrey = new Color(40,40,40);
    public static int maxCountryPoints = 25;

    private static double scaleX = 1.0;
    private static double scaleY = 1.0;

    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    public static DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public static void setDifficultyLevel(DifficultyLevel level) {
        difficultyLevel = level;
    }


    public static double getScaleX() {
        return scaleX;
    }

    public static double getScaleY() {
        return scaleY;
    }

    public static void updateScales(int newWidth, int newHeight) {
        scaleX = (double) newWidth / windowWidth;
        scaleY = (double) newHeight / windowHeight;
    }
}
