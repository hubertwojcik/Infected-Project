package controllers.game;
import controllers.AppController;
import controllers.map.MapController;
import models.map.Country;
import models.game.GameModel;
import views.game.GameView;

public class GameController implements Runnable{
    //MODELS
    private final GameModel gameModel;
    //CONTROLLERS
    private final MapController mapController;
    //VIEWS
    private final GameView gameView;
    //LOOP
    private boolean isRunning;
    private Thread gameThread;

    public GameController(AppController appController) {
        //MODELS
        this.gameModel = new GameModel();
        //CONTROLLERS
        this.mapController = new MapController(gameModel,this);
        this.gameView = new GameView(this, gameModel,mapController);

    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public GameView getGameView() {
        return gameView;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                updateGameState();
                Thread.sleep(1000); // Odczekaj 1 sekundę
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void startNewGame() {
        resetGame();
        startGameLoop();
        gameView.showGameStartAlert();
    }

    public void resumeGame() {
        startGameLoop();
    }

    public void pauseGame() {
        stopGameLoop();
    }

    public void stopGame() {
        stopGameLoop();
        resetGame();
    }

    public void resetGame() {
        gameModel.initializeGameData();
    }

    private void startGameLoop() {
        if (gameThread == null || !gameThread.isAlive()) {
            isRunning = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    private void stopGameLoop() {
        isRunning = false;
        if (gameThread != null) {
            gameThread.interrupt();
        }
    }

    private void updateGameState() {
        gameModel.updateModel();
        updateGameViews(); // Zaktualizuj widok
    }
    public void updateGameViews(){
        gameView.updateGameViews();
    }

    public void handleCountrySidebarClick() {
        Country selectedCountry = gameModel.getSelectedCountry();
        if (selectedCountry == null) {
            gameView.getGameSidebarView().hideCountryPanel();
        } else {
            gameView.getGameSidebarView().updateCountryPanel();
        }
    }

}
