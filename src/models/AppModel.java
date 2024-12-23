package models;

import enums.GameState;

public class AppModel {
    private GameState gameState;

    public AppModel() {
        this.gameState = GameState.NOT_STARTED;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }


}
