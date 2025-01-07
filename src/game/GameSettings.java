package game;

import enums.DifficultyLevel;

public class GameSettings {
    private static GameSettings instance;
    public static int simulationInterval = 1000;
    public static int windowWidth = 800;
    public static int windowHeight = 600;
    public static int sidebarWidht = 200;
    public static int mapWidth = windowWidth - sidebarWidht;
    private static DifficultyLevel difficultyLevel = DifficultyLevel.EASY;

    //TODO SINGELOTTON PATTERN
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

}
