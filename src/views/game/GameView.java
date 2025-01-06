package views.game;

import controllers.game.GameController;
import controllers.map.MapController;
import javax.swing.*;
import java.awt.*;
import models.game.GameModel;
import views.map.MapView;

public class GameView extends JPanel {
    private final MapView mapView;

    //VIEWS
    private final GameHeaderView gameHeaderView;
    private GameSidebarView gameSidebarView;

    public GameView(GameController gameController, GameModel gameModel, MapController mapController) {
        //MODELS
        //CONTROLLERS
        //Views
        this.setLayout(new BorderLayout());
        gameHeaderView = new GameHeaderView(gameController, gameModel);
        //GAME HEADER
        this.add(gameHeaderView, BorderLayout.NORTH);
        //GAME MAP
        mapView = new MapView(gameModel, mapController);
        this.add(mapView, BorderLayout.CENTER);
        //GAME SIDE BAR
        gameSidebarView = new GameSidebarView(gameModel);
        this.add(gameSidebarView,BorderLayout.EAST);
    }

    public GameSidebarView getGameSidebarView(){
        return gameSidebarView;
    }

    public void updateGameViews() {
            gameHeaderView.updateGameDateLabel();;
            gameSidebarView.updateCountryPanel();
    }

    public void showGameStartAlert() {
        // Wyświetlenie okna dialogowego z alertem
        JOptionPane.showMessageDialog(
                this,
                "W prowincji Huber wybuchła pozornie niewinna epidemia.\n" +
                        "Rozprzestrzenia się szybciej, niż ktokolwiek mógł przypuszczać.",
                "Początek epidemii",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


}
