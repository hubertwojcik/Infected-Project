import enums.GameState;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    GameState gameState = GameState.NOT_STARTED;
    GameFrame gameFrame;

    public GamePanel(){
        gameFrame = new GameFrame();
        // Inicjalizacja ekranów
        initializeGameScreens();
        setGameState(GameState.NOT_STARTED);


        gameFrame.setVisible(true);
    }

    public void setGameState(GameState gameState){
        this.gameState = gameState;

        CardLayout layout = (CardLayout) gameFrame.getContentPane().getLayout();

        switch (gameState){
            case NOT_STARTED -> layout.show(gameFrame.getContentPane(),"START");
            case PLAYING -> layout.show(gameFrame.getContentPane(), "GAME");
            case PAUSED -> layout.show(gameFrame.getContentPane(), "PAUSE");
        }
    }

    public void update(){}

    public void draw(){}


    private void initializeGameScreens(){
        gameFrame.add(new StartGameScreen(this), "START");
        gameFrame.add(new GameScreen(this), "GAME");
        gameFrame.add(new PauseScreen(this), "PAUSE");
    }
}
