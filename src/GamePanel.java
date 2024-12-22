import enums.GameState;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    int FPS = 60;
    private boolean isRunning = false;


    GameState gameState = GameState.NOT_STARTED;
    GameFrame gameFrame;


    public GamePanel(){
        gameFrame = new GameFrame();

        initializeGameScreens();

        setGameState(GameState.NOT_STARTED);

        gameFrame.setVisible(true);
    }

    public void setGameState(GameState gameState){
        this.gameState = gameState;

        CardLayout layout = (CardLayout) gameFrame.getContentPane().getLayout();

        switch (gameState) {
            case NOT_STARTED -> {
                layout.show(gameFrame.getContentPane(), "START");
                pauseGame();
            }
            case PLAYING -> {
                layout.show(gameFrame.getContentPane(), "GAME");
                resumeGame();
            }
            case PAUSED -> {
                layout.show(gameFrame.getContentPane(), "PAUSE");
                pauseGame();
            }
        }
    }

    @Override
    public void run() {
        isRunning = true;
        double drawInterval = (double) 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (isRunning){
            if (gameState == GameState.PAUSED) {
                continue;
            }

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1_000_000_000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

    }

    public void pauseGame() {
        isRunning = false;
    }

    public void resumeGame() {
        if (!isRunning) {
            isRunning = true;
            new Thread(this).start();
        }
    }

    private void initializeGameScreens(){
        gameFrame.add(new StartGameScreen(this), "START");
        gameFrame.add(new GameScreen(this), "GAME");
        gameFrame.add(new PauseScreen(this), "PAUSE");
    }



}
