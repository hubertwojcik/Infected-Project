public class GameLoop implements Runnable {
    private GamePanel gamePanel;
    private boolean isRunning = false; // Controls the game loop
    private final int FPS = 60;

    public GameLoop(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void run() {
        isRunning = true;

        double drawInterval = (double) 1_000_000_000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(isRunning){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                updateGame();
                renderFrame();
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
    public void stop() {
        isRunning = false; // Gracefully stops the game loop
    }

    private void updateGame() {
        // Game logic updates\
        gamePanel.update();

    }
    private void renderFrame() {
        // Rendering logic
        gamePanel.draw();
    }



}
